package com.rh.note.view;

import cn.hutool.core.io.IoUtil;
import com.rh.note.builder.AdocTextPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.AdocTextPane;
import com.rh.note.config.SyntaxHighlightConfig;
import com.rh.note.exception.AdocTextPaneInitContentException;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.TextPaneWriterToFileException;
import com.rh.note.path.FileBeanPath;
import com.rh.note.style.AdocFontStyle;
import com.rh.note.style.StyleList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;
import java.io.Reader;
import java.io.Writer;

/**
 * adoc编辑区 视图
 */
@Slf4j
public class AdocTextPaneView extends BaseView<AdocTextPaneBuilder, AdocTextPane> {

    public @Nullable AdocTextPaneView init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return super.init(filePath);
    }

    protected AdocTextPane textPane() {
        return super.getComponent(AdocTextPaneBuilder::getTextPane);
    }

    public @NotNull AdocTextPaneView create(@NotNull FileBeanPath beanPath) {
        return super.create(beanPath);
    }

    /**
     * 写入文件
     */
    public void writerToFile(Writer writer) {
        if (writer == null) {
            return;
        }
        try {
            textPane().write(writer);
        } catch (Exception e) {
            throw new TextPaneWriterToFileException(e);
        } finally {
            IoUtil.close(writer);
        }
    }

    /**
     * 初始化编辑区内容
     */
    public void initContent(Reader reader) {
        if (reader == null) {
            return;
        }
        try {
            textPane().read(reader, null);
        } catch (Exception e) {
            throw new AdocTextPaneInitContentException(e);
        } finally {
            IoUtil.close(reader);
        }
    }

    /**
     * 获得光标行的内容
     * tip 如果不是最后一行, 也包含换行符
     */
    public @Nullable String getCaretLineContent() {
        Element element = getCaretLineElement();
        if (element == null) {
            return null;
        }
        try {
            return textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_CONTENT_OF_THE_EDIT_AREA, e);
        }
    }

    /**
     * 关闭编辑区
     */
    public void close() {
        super.destroy();
    }

    /**
     * 选择光标行
     * tip 不包括换行符
     */
    public void selectCaretLine() {
        Element element = getCaretLineElement();
        if (element == null) {
            return;
        }
        String lineContent = null;
        try {
            lineContent = textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
        } catch (Exception e) {
            log.error("[选择光标行]-[判断行是否以换行符结尾] error", e);
        }
        if (lineContent == null) {
            return;
        }
        int selectionEnd = lineContent.endsWith("\n") ? element.getEndOffset() - 1 : element.getEndOffset();
        textPane().select(element.getStartOffset(), selectionEnd);
    }

    /**
     * 获得光标行元素
     */
    private @Nullable Element getCaretLineElement() {
        int dot = textPane().getCaret().getDot();
        if (dot < 0) {
            return null;
        }
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(dot);
        if (index < 0) {
            return null;
        }
        return rootElement.getElement(index);
    }

    /**
     * 替换被选择的内容
     */
    public void replaceSelectedContent(String newContent) {
        if (newContent == null) {
            return;
        }
        textPane().replaceSelection(newContent);
    }

    /**
     * 初始化文件内容
     */
    public void initContent(String initContent) {
        if (StringUtils.isBlank(initContent)) {
            return;
        }
        textPane().setText(initContent);
    }

    /**
     * 刷新语法高亮
     */
    public void refreshSyntaxHighlight(SyntaxHighlightConfig syntaxHighlightConfig) {
        if (syntaxHighlightConfig == null) {
            return;
        }
        AdocFontStyle adocFontStyle = syntaxHighlightConfig.getAdocFontStyle();
        if (adocFontStyle == null) {
            return;
        }
        StyledDocument styledDocument = textPane().getStyledDocument();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        for (int i = 0; i < rootElement.getElementCount(); i++) {
            Element element = rootElement.getElement(i);
            String lineContent;
            try {
                lineContent = textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
            } catch (Exception e) {
                throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_CONTENT_OF_THE_EDIT_AREA, e);
            }
            StyleList list = adocFontStyle.generateStyle(lineContent);
            if (list == null || list.isEmpty()) {
                continue;
            }
            list.forEach(item ->
                    styledDocument.setCharacterAttributes(element.getStartOffset() + item.getOffset(), item.getLength(), item.getStyle(), false)
            );
        }
    }

    /**
     * 光标行前一行的换行符恢复为默认风格
     * for 不将前一行的语法高亮样式带到下一行
     */
    public void clearFontStyleBeforeCaretLine(SyntaxHighlightConfig syntaxHighlightConfig) {
        // 获取光标前一行元素
        int dot = textPane().getCaret().getDot();
        if (dot < 0) {
            return;
        }
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(dot) - 1;
        if (index < 0) {
            return;
        }
        Element element = rootElement.getElement(index);
        if (element == null) {
            return;
        }
        // 判断行内容是以\n结尾的
        if (element.getEndOffset() - element.getStartOffset() <= 0) {
            return;
        }
        String lineContext = null;
        try {
            lineContext = textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
        } catch (Exception e) {
            log.error("[光标行前一行的换行符恢复为默认风格]-[获得编辑区行内容失败] error", e);
        }
        if (StringUtils.isBlank(lineContext) || !lineContext.endsWith("\n")) {
            return;
        }
        // 设置为默认样式
        StyledDocument document = textPane().getStyledDocument();
        SimpleAttributeSet defaultStyle = syntaxHighlightConfig.getDefaultStyle();
        document.setCharacterAttributes(element.getEndOffset() - 1, element.getEndOffset(), defaultStyle, true);
    }
}
