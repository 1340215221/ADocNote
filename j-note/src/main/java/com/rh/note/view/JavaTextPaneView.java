package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.JavaTextPaneBuilder;
import com.rh.note.component.JavaTextPane;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.UnknownLogicException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * java文件编辑控件
 */
public class JavaTextPaneView extends Init<JavaTextPane> {

    public static void create(String absolutePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        new JavaTextPaneBuilder(absolutePath).init();
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
}
