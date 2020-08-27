package com.rh.note.view;

import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.component.NoteTextPane;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.NoteException;
import com.rh.note.grammar.IncludeGrammar;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.AttributeSet;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Element;
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
@Slf4j
public class TextPaneRunView extends Init<NoteTextPane> {

    /**
     * 编辑区控件
     */
    private static final List<String> openedTextPaneComponentIds = new ArrayList<>();

    public static void create(String filePath) {
        new TextPaneBuilder(filePath).init();
        openedTextPaneComponentIds.add(TextPaneBuilder.id(filePath));
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
            throw new NoteException(ErrorMessage.file_read_failed, e);
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
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(caret.getDot());
        if (index < 0) {
            log.error(ErrorCodeEnum.FAILED_TO_GET_CURRENT_LINE_CONTENT.getMsg());
            return "";
        }
        Element element = rootElement.getElement(index);
        if (element == null) {
            log.error(ErrorCodeEnum.FAILED_TO_GET_CURRENT_LINE_CONTENT.getMsg());
            return "";
        }
        try {
            return textPane().getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
        } catch (Exception e) {
            log.error(ErrorCodeEnum.FAILED_TO_GET_CURRENT_LINE_CONTENT.getMsg(), e);
            return "";
        }
    }

    /**
     * 替换选中内容
     */
    public void replaceSelectContent(String newContent) {
        if (StringUtils.isBlank(newContent)) {
            return;
        }
        textPane().replaceSelection(newContent);
        // 光标向前移动一行
        int dot = textPane().getCaret().getDot();
        textPane().getCaret().setDot(dot - 2);
    }

    /**
     * 选择当前行
     */
    public boolean selectCurrentLine() {
        int dot = textPane().getCaret().getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(dot);
        if (index < 0) {
            return false;
        }
        Element element = rootElement.getElement(index);
        if (element == null) {
            return false;
        }
        textPane().select(element.getStartOffset(), element.getEndOffset());
        return true;
    }

    /**
     * 选择include文件名
     */
    public boolean selectIncludeFileName(IncludeGrammar includeGrammar) {
        if (includeGrammar == null) {
            return false;
        }
        int dot = textPane().getCaret().getDot();
        Element rootElement = textPane().getDocument().getDefaultRootElement();
        int index = rootElement.getElementIndex(dot);
        if (index < 0) {
            return false;
        }
        Element element = rootElement.getElement(index);
        if (element == null) {
            return false;
        }
        textPane().select(element.getStartOffset() + includeGrammar.getStartOffset(),
                element.getStartOffset() + includeGrammar.getEndOffset());
        return true;
    }

    /**
     * 获得选中的内容
     * 注意, 由于选中当前行后 光标移动到了下一行, 不能再获取当前行内容了
     */
    public String getSelectContent() {
        return textPane().getSelectedText();
    }

    /**
     * 输入回车
     */
    public void insertEnter() throws Exception {
        int dot = textPane().getCaret().getDot();
        AttributeSet attributeSet = textPane().getCharacterAttributes();
        Document document = textPane().getDocument();
        document.insertString(dot, "\n", attributeSet);
    }

    /**
     * 遍历编辑区接口
     */
    public interface IForEach {
        void handle(TextPaneRunView view);
    }
}
