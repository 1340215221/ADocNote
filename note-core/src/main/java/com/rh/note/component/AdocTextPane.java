package com.rh.note.component;

import com.rh.note.common.IFileBeanPath;
import com.rh.note.common.ChangeContextListener;
import com.rh.note.path.FileBeanPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.IOException;
import java.io.Reader;

/**
 * 编辑面板
 */
@Getter
@Setter
@NoArgsConstructor
public class AdocTextPane extends JTextPane implements IFileBeanPath {
    /**
     * 固定的输入样式
     * 输入样式不继承前一个字符
     */
    private FixedInputAttributeSet inputStyle;
    /**
     * 对象地址
     */
    private FileBeanPath beanPath;
    /**
     * 行间距
     */
    private Float lineSpacing;
    /**
     * 写入内容监听
     */
    private ChangeContextListener contentChanged;

    public AdocTextPane(StyledDocument doc) {
        super(doc);
    }

    @Override
    public void read(Reader reader, Object desc) throws IOException {
        EditorKit kit = getUI().getEditorKit(this);
        Document doc = kit.createDefaultDocument();
        initLineSpacing((StyledDocument) doc); // 设置行间距样式
        if (desc != null) {
            doc.putProperty(Document.StreamDescriptionProperty, desc);
        }
        try {
            kit.read(reader, doc, 0);
            setDocument(doc);
        } catch (BadLocationException e) {
            throw new IOException(e.getMessage());
        }
        if (contentChanged != null) {
            contentChanged.run(beanPath.getFilePath());
        }
    }

    public void setLineSpacing(Float lineSpacing) {
        this.lineSpacing = lineSpacing;
        initLineSpacing();
    }

    @Override
    public @NotNull MutableAttributeSet getInputAttributes() {
        if (inputStyle == null) {
            inputStyle = new FixedInputAttributeSet(this);
        }
        return inputStyle;
    }

    /**
     * 设置行间距样式
     */
    private void initLineSpacing() {
        initLineSpacing(getStyledDocument());
    }

    /**
     * 设置行间距样式
     */
    private void initLineSpacing(StyledDocument document) {
        if (lineSpacing == null || lineSpacing <= 0f || document == null) {
            return;
        }
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(set, lineSpacing);
        Element element = document.getDefaultRootElement();
        document.setParagraphAttributes(element.getStartOffset(), element.getEndOffset(), set, false);
    }

    @Override
    public void replaceSelection(String content) {
        super.replaceSelection(content);
        if (contentChanged != null) {
            contentChanged.run(beanPath.getFilePath());
        }
    }

    @Override
    public void setText(String t) {
        super.setText(t);
        if (contentChanged != null) {
            contentChanged.run(beanPath.getFilePath());
        }
    }
}
