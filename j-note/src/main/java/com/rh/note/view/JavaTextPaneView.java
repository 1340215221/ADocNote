package com.rh.note.view;

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
import java.io.File;
import java.io.FileReader;

/**
 * java文件编辑控件
 */
public class JavaTextPaneView extends Init<JavaTextPane> {

    /**
     * todo 方法参数有待封装成对象
     */
    public static void create(String absolutePath, String sourceFilePath, String includeFilePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        new JavaTextPaneBuilder(absolutePath, sourceFilePath, includeFilePath).init();
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
}
