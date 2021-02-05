package com.rh.note.component;

import com.rh.note.common.IFileBeanPath;
import com.rh.note.path.FileBeanPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.text.*;
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
     * 对象地址
     */
    private FileBeanPath beanPath;
    /**
     * 行间距
     */
    private Float lineSpacing;

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
    }

    public void setLineSpacing(Float lineSpacing) {
        this.lineSpacing = lineSpacing;
        initLineSpacing();
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
}
