package com.rh.note.view;

import cn.hutool.core.io.IoUtil;
import com.rh.note.bean.SyntaxStyleContext;
import com.rh.note.builder.JavaTextPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.JavaTextPane;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.TextPaneInitContentException;
import com.rh.note.path.FileBeanPath;
import com.rh.note.style.StyleList;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import java.io.Reader;

/**
 * java编辑区
 */
public class JavaTextPaneView extends BaseView<JavaTextPaneBuilder, JavaTextPane> {

    public @NotNull JavaTextPaneView create(@NonNull FileBeanPath beanPath) {
        return super.create(beanPath);
    }

    public @Nullable JavaTextPaneView init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return super.init(filePath);
    }

    /**
     * 初始化内容
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

    protected @NotNull JavaTextPane textPane() {
        return super.getComponent(JavaTextPaneBuilder::getTextPane);
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

    /**
     * 关闭编辑区
     */
    public void close() {
        super.destroy();
    }
}
