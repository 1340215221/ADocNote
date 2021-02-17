package com.rh.note.view;

import cn.hutool.core.io.IoUtil;
import com.rh.note.bean.SyntaxStyleContext;
import com.rh.note.builder.AdocTextPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.AdocTextPane;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.TextPaneInitContentException;
import com.rh.note.exception.TextPaneWriterToFileException;
import com.rh.note.path.FileBeanPath;
import com.rh.note.style.StyleList;
import com.rh.note.syntax.TitleSyntax;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Element;
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
            throw new TextPaneInitContentException(e);
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


    public void updateCaretLineContent(String newLineContent) {
        if (StringUtils.isBlank(newLineContent)) {
            return;
        }
        Element element = getCaretLineElement();
        if (element == null) {
            return;
        }
        String lineContent;
        try {
            lineContent = textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_CONTENT_OF_THE_EDIT_AREA, e);
        }
        int selectionEnd = StringUtils.isNotBlank(lineContent) && lineContent.endsWith("\n") ?
                element.getEndOffset() - 1 : element.getEndOffset();
        textPane().select(element.getStartOffset(), selectionEnd);
        textPane().replaceSelection(newLineContent);
    }

    /**
     * 更新adoc编辑区根标题名字
     */
    public void updateRootTitleName(String newTitleName) {
        if (StringUtils.isBlank(newTitleName)) {
            return;
        }
        // 找到根标题行
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        for (int i = 1; i < rootElement.getElementCount() + 1; i++) {
            Element element = rootElement.getElement(i);
            if (element == null) {
                continue;
            }
            String lineContent;
            try {
                lineContent = textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
            } catch (Exception e) {
                throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_CONTENT_OF_THE_EDIT_AREA, e);
            }
            TitleSyntax syntax = new TitleSyntax().init(lineContent);
            if (syntax == null) {
                continue;
            }
            // 更新根标题名
            int selectionStart = element.getStartOffset() + syntax.getLevel() + 1;
            int selectionEnd = lineContent.endsWith("\n") ? element.getEndOffset() - 1 : element.getEndOffset();
            textPane().select(selectionStart, selectionEnd);
            textPane().replaceSelection(newTitleName);
            return;
        }
        throw new ApplicationException(ErrorCodeEnum.FAILED_TO_UPDATE_FILE_ROOT_TITLE_NAME);
    }

    /**
     * 删除光标行内容
     */
    public void deleteCaretLine() {
        Element element = this.getCaretLineElement();
        if (element == null) {
            return;
        }
        textPane().select(element.getStartOffset(), element.getEndOffset());
        textPane().replaceSelection("");
    }

    /**
     * 遍历行
     */
    public void forEachLine(SyntaxStyleContext styleContext) {
        if (styleContext == null) {
            return;
        }
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        for (int i = 0; i < rootElement.getElementCount(); i++) {
            Element element = rootElement.getElement(i);
            if (element == null) {
                continue;
            }
            // 清除默认样式
            StyledDocument styledDocument = textPane().getStyledDocument();
            styledDocument.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), styleContext.getDefaultStyle(), true);
            // 设置新样式
            String lineContent;
            try {
                lineContent = textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
            } catch (Exception e) {
                throw new ApplicationException(ErrorCodeEnum.FAILED_TO_GET_THE_CONTENT_OF_THE_EDIT_AREA, e);
            }
            styleContext.read(lineContent);
            StyleList list = styleContext.getEnableStyle();
            list.forEach(item ->
                    styledDocument.setCharacterAttributes(element.getStartOffset() + item.getOffset(), item.getLength(), item.getStyle(), false));
        }
    }
}
