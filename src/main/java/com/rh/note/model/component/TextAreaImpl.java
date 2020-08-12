package com.rh.note.model.component;

import com.rh.note.constant.ErrorMessage;
import com.rh.note.constant.GroovyCommonConstant;
import com.rh.note.exception.AdocException;
import com.rh.note.view.TextArea;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.text.Caret;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 编辑区
 */
public class TextAreaImpl extends Init<JTextArea> {

    public TextAreaImpl initByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return super.init(TextArea.id(filePath));
    }

    /**
     * 读取文件内容到编辑区
     */
    public void read(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return;
        }
        try (InputStream is = new FileInputStream(file); Reader read = new InputStreamReader(is)) {
            textArea().read(read, null);
        } catch (Exception e) {
            throw new AdocException(ErrorMessage.file_read_failed);
        }
    }

    /**
     * 创建一个编辑区域
     */
    public static void create(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        new TextArea(filePath).init(GroovyCommonConstant.closure);
    }

    /**
     * 获取光标所在行的内容
     */
    public String getLineContentOfTextArea() throws Exception {
        Caret caret = textArea().getCaret();
        int line = textArea().getLineOfOffset(caret.getDot());
        int startOffset = textArea().getLineStartOffset(line);
        int endOffset = textArea().getLineEndOffset(line);
        return textArea().getText(startOffset, endOffset - startOffset);
    }

    private JTextArea textArea() {
        return getBean();
    }

    /**
     * 在控件中更新include为新文件名字
     */
    public void replaceName(String oldTitleName, String newTitleName) throws Exception {
        if (StringUtils.isBlank(oldTitleName) || StringUtils.isBlank(newTitleName)) {
            return;
        }
        Caret caret = textArea().getCaret();
        int line = textArea().getLineOfOffset(caret.getDot());
        int endOffset = textArea().getLineEndOffset(line);
        int index = endOffset - ".adoc[]".length() - oldTitleName.length();
        textArea().replaceRange(newTitleName, index, index + oldTitleName.length());
    }
}
