package com.rh.note.view;

import com.rh.note.builder.TextAreaBuilder;
import com.rh.note.component.AdocTextArea;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.NoteException;
import com.rh.note.grammar.IncludeGrammar;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
public class TextAreaRunView extends Init<AdocTextArea> {

    /**
     * 编辑区控件
     */
    private static final List<String> openedTextAreaComponentIds = new ArrayList<>();

    @Override
    public TextAreaRunView init(String componentId) {
        return super.init(componentId);
    }

    public TextAreaRunView initByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return super.init(TextAreaBuilder.id(filePath));
    }

    /**
     * 遍历编辑区控件
     */
    public static void forEach(@NonNull IForEach forEach) {
        if (CollectionUtils.isEmpty(openedTextAreaComponentIds)) {
            return;
        }
        openedTextAreaComponentIds.forEach(componentId ->
            forEach.handle(new TextAreaRunView().init(componentId)));
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
            throw new NoteException(ErrorMessage.file_read_failed);
        }
    }

    /**
     * 创建一个编辑区域
     */
    public static void create(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        new TextAreaBuilder(filePath).init();
        openedTextAreaComponentIds.add(TextAreaBuilder.id(filePath));
    }

    /**
     * 获取光标所在行的内容
     */
    public String getLineContent() throws Exception {
        Caret caret = textArea().getCaret();
        int line = textArea().getLineOfOffset(caret.getDot());
        int startOffset = textArea().getLineStartOffset(line);
        int endOffset = textArea().getLineEndOffset(line);
        return textArea().getText(startOffset, endOffset - startOffset);
    }

    private AdocTextArea textArea() {
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

    /**
     * 编辑文件路径
     */
    public String getFilePath() {
        AdocTextArea textArea = textArea();
        return textArea.getFilePath();
    }

    /**
     * 替换include语句
     */
    public void replaceIncludeGrammar(IncludeGrammar include) throws Exception {
        if (include == null) {
            return;
        }
        // todo
        // 生成include语句
        String grammar = include.generateGrammar();
        // 在编辑区中修改
        Caret caret = textArea().getCaret();
        int line = textArea().getLineOfOffset(caret.getDot());
        int startOffset = textArea().getLineStartOffset(line);
        int endOffset = textArea().getLineEndOffset(line);
        textArea().replaceRange(grammar, startOffset, endOffset);
    }

    /**
     * 将编辑区内容写入到文件
     */
    public void write(Writer writer) {
        if (writer == null) {
            return;
        }

        try {
            textArea().write(writer);
        } catch (Exception e) {
            throw new NoteException(ErrorMessage.file_write_failed);
        }
    }

    /**
     * 遍历编辑区接口
     */
    public interface IForEach {
        void handle(TextAreaRunView view);
    }
}
