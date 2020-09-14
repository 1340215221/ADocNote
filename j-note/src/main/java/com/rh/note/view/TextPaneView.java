package com.rh.note.view;

import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCode;
import com.rh.note.file.AdocFile;
import com.rh.note.util.Init;

import javax.swing.JTextPane;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 编辑区视图
 * {@link com.rh.note.builder.TextPaneBuilder#id}
 */
public class TextPaneView extends Init<JTextPane> {

    public static void create(AdocFile adocFile) {
        if (adocFile == null) {
            return;
        }
        new TextPaneBuilder(adocFile).init();
    }

    public TextPaneView initByFilePath(String filePath) {
        return super.init(TextPaneBuilder.id(filePath));
    }

    private JTextPane textPane() {
        return getBean();
    }

    /**
     * 编辑区内容是否为空
     */
    public boolean isBlank() {
        return textPane().getDocument().getLength() < 1;
    }

    /**
     * 加载文件内容
     */
    public void read(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return;
        }
        try (InputStream is = new FileInputStream(file); Reader read = new InputStreamReader(is)) {
            textPane().read(read, null);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.file_read_failed, e);
        }
    }
}
