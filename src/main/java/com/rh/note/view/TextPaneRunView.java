package com.rh.note.view;

import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.component.AdocTextArea;
import com.rh.note.component.NoteTextPane;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.NoteException;
import com.rh.note.grammar.IncludeGrammar;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.text.Caret;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * 编辑区
 */
public class TextPaneRunView extends Init<NoteTextPane> {

    /**
     * 编辑区控件
     */
    private static final List<String> openedTextPaneComponentIds = new ArrayList<>();

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

    private NoteTextPane textPane() {
        return getBean();
    }

    /**
     * 遍历编辑区控件
     */
    public static void forEach(@NonNull IForEach forEach) {
        if (CollectionUtils.isEmpty(openedTextPaneComponentIds)) {
            return;
        }
        openedTextPaneComponentIds.forEach(componentId ->
                forEach.handle(new TextPaneRunView().init(componentId)));
    }

    /**
     * 设置文件地址
     */
    public String getFilePath() {
        NoteTextPane textPane = textPane();
        return textPane.getFilePath();
    }

    /**
     * 写入编辑控件内容
     */
    public void write(Writer writer) {
        if (writer == null) {
            return;
        }

        try {
            textPane().write(writer);
        } catch (Exception e) {
            throw new NoteException(ErrorMessage.file_write_failed);
        }
    }

    /**
     * 获得光标所在行内容
     */
    public String getLineContent() {
        Caret caret = textPane().getCaret();
        // todo
//        int line = textPane().getLineOfOffset(caret.getDot());
//        int startOffset = textPane().getLineStartOffset(line);
//        int endOffset = textPane().getLineEndOffset(line);
//        return textPane().getText(startOffset, endOffset - startOffset);
        return null;
    }

    /**
     * 替换include语法块
     */
    public void replaceIncludeGrammar(IncludeGrammar include) {
        //todo
    }

    /**
     * 在编辑控件中修改为新的语法语句
     */
    public void replaceName(String titleName, String newTitleName) {
        //todo
    }

    /**
     * 遍历编辑区接口
     */
    public interface IForEach {
        void handle(TextPaneRunView view);
    }
}
