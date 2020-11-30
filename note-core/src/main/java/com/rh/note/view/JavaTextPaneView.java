package com.rh.note.view;

import com.rh.note.ao.MarkLineAO;
import com.rh.note.base.Init;
import com.rh.note.builder.JavaTextPaneBuilder;
import com.rh.note.component.JavaTextPane;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Caret;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;
import java.io.File;
import java.io.FileReader;

/**
 * java文件编辑控件
 */
public class JavaTextPaneView extends Init<JavaTextPane> {

    /**
     * todo 方法参数有待封装成对象
     */
    public static void create(String absolutePath, String sourceFilePath, String includeFilePath, Integer markLineNumber1, Integer markLineNumber2) {
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        new JavaTextPaneBuilder(absolutePath, sourceFilePath, includeFilePath, markLineNumber1, markLineNumber2).init();
    }

    public static @Nullable JavaTextPaneView cast(JavaTextPane bean) {
        if (bean == null) {
            return null;
        }
        return new JavaTextPaneView().init(bean.getAbsolutePath());
    }

    public @Nullable JavaTextPaneView init(String absolutePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return null;
        }
        return super.init(JavaTextPaneBuilder.id(absolutePath));
    }

    private @NotNull JavaTextPane textPane() {
        return getBean();
    }

    public void write() {
        String absolutePath = textPane().getAbsolutePath();
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        File file = new File(absolutePath);
        if (!file.exists() || !file.isFile()) {
            return;
        }
        try (FileReader reader = new FileReader(file)) {
            textPane().read(reader, null);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.READ_FILE_ERROR, e);
        }
    }

    /**
     * 获得光标所在行号
     */
    public @Nullable Integer getLineNumberByCaret() {
        Caret caret = textPane().getCaret();
        if (!caret.isVisible()) {
            return null;
        }
        int dot = caret.getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(dot);
        if (index < 0) {
            return null;
        }
        return index + 1;
    }

    /**
     * 来源编辑区文件路径
     */
    public @NotNull String getSourceFilePath() {
        return textPane().getSourceFilePath();
    }

    /**
     * include java中的文件路径
     */
    public @NotNull String getIncludeFilePath() {
        return textPane().getIncludeFilePath();
    }

    /**
     * todo
     * 更新光标行标记颜色
     */
    public void updateMarkColorOnCaretLine(MarkLineAO ao) {
        // 清除旧标记颜色
        Integer oldMarkLineNumber = null;
        if (ao.isCtrlOne()) {
            oldMarkLineNumber = textPane().getMarkLineNumber1();
        }
        if (ao.isCtrlTwo()) {
            oldMarkLineNumber = textPane().getMarkLineNumber2();
        }
        if (oldMarkLineNumber != null) {
            Element rootElement = textPane().getDocument().getDefaultRootElement();
            Element element = rootElement.getElement(oldMarkLineNumber - 1);

            if (element != null) {
                SimpleAttributeSet aSet = new SimpleAttributeSet();
                StyleConstants.setBackground(aSet, textPane().getBackground());
                StyledDocument doc = textPane().getStyledDocument();

                doc.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), aSet, false);
            }
        }

        // 更新新标记颜色
        int dot = textPane().getCaret().getDot();

        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int elementIndex = rootElement.getElementIndex(dot);
        Element element = rootElement.getElement(elementIndex);
        if (element == null) {
            return;
        }

        SimpleAttributeSet aSet = new SimpleAttributeSet();
        StyleConstants.setBackground(aSet, Color.gray);
        StyledDocument doc = textPane().getStyledDocument();

        doc.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), aSet, false);
        // 记录标记
        if (ao.isCtrlOne()) {
            textPane().setMarkLineNumber1(ao.getLineNumber());
        }
        if (ao.isCtrlTwo()) {
            textPane().setMarkLineNumber2(ao.getLineNumber());
        }
    }

    /**
     * todo
     * 初始化标记行颜色
     */
    public void initMarkLineColor() {
        Element rootElement = textPane().getDocument().getDefaultRootElement();

        Integer markLineNumber1 = textPane().getMarkLineNumber1();
        if (markLineNumber1 != null) {
            Element element = rootElement.getElement(markLineNumber1 - 1);

            if (element != null) {
                SimpleAttributeSet aSet = new SimpleAttributeSet();
                StyleConstants.setBackground(aSet, Color.gray);
                StyledDocument doc = textPane().getStyledDocument();

                doc.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), aSet, false);
            }
        }

        Integer markLineNumber2 = textPane().getMarkLineNumber2();
        if (markLineNumber2 != null) {
            Element element = rootElement.getElement(markLineNumber2 - 1);

            if (element != null) {
                SimpleAttributeSet aSet = new SimpleAttributeSet();
                StyleConstants.setBackground(aSet, Color.gray);
                StyledDocument doc = textPane().getStyledDocument();

                doc.setCharacterAttributes(element.getStartOffset(), element.getEndOffset() - element.getStartOffset(), aSet, false);
            }
        }
    }
}
