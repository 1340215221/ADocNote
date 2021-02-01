package com.rh.note.view;

import cn.hutool.core.io.IoUtil;
import com.rh.note.builder.AdocTextPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.AdocTextPane;
import com.rh.note.exception.AdocTextPaneInitContentException;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.TextPaneWriterToFileException;
import com.rh.note.path.FileBeanPath;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Element;
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
        int selectionEnd = lineContent.endsWith("\\n") ? element.getEndOffset() - 1 : element.getEndOffset();
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
}
