package com.rh.note.view;

import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.NoteException;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 编辑区
 */
public class TextPaneRunView extends Init<JTextPane> {

    public static void create(String filePath) {
        new TextPaneBuilder(filePath).init();
    }

    @Override
    public TextPaneRunView init(String componentId) {
        if (StringUtils.isBlank(componentId)) {
            return null;
        }
        return super.init(componentId);
    }

    public TextPaneRunView initByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return super.init(TextPaneBuilder.id(filePath));
    }

    public void read(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return;
        }
        try (InputStream is = new FileInputStream(file); Reader read = new InputStreamReader(is)) {
            textPane().read(read, null);
        } catch (Exception e) {
            throw new NoteException(ErrorMessage.file_read_failed);
        }
    }

    private JTextPane textPane() {
        return getBean();
    }
}
