package com.rh.note.component;

import com.rh.note.common.ChangeContextListener;
import com.rh.note.plug.ILineSpacingTextPane;
import com.rh.note.plug.IReadOnlyTextPane;
import com.rh.note.path.FileBeanPath;
import com.rh.note.plug.NoLineWrapTextPanePlug;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledDocument;
import java.io.IOException;
import java.io.Reader;

/**
 * java编辑区
 */
@Setter
@Getter
public class JavaTextPane extends JTextPane implements IReadOnlyTextPane, ILineSpacingTextPane {
    /**
     * 不自动换行插件
     */
    @Delegate
    private NoLineWrapTextPanePlug plug = new NoLineWrapTextPanePlug(this);
    /**
     * 对象地址
     */
    private FileBeanPath beanPath;
    /**
     * 行间距
     */
    private Float lineSpacing;
    /**
     * 内容变更监听
     */
    private ChangeContextListener contentChanged;

    public JavaTextPane() {
        super();
        this.setReadOnly();
    }

    public JavaTextPane(StyledDocument doc) {
        super(doc);
        this.setReadOnly();
    }

    @Override
    public void read(Reader reader, Object desc) throws IOException {
        EditorKit kit = getUI().getEditorKit(this);
        Document doc = kit.createDefaultDocument();
        this.initLineSpacing((StyledDocument) doc); // 设置行间距样式
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
        this.initLineSpacing();
    }
}
