package com.rh.note.component;

import com.rh.note.common.ChangeContextListener;
import com.rh.note.common.IFileBeanPath;
import com.rh.note.path.FileBeanPath;
import com.rh.note.plug.ILineSpacingTextPane;
import com.rh.note.plug.NoLineWrapTextPanePlug;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyledDocument;
import java.io.IOException;
import java.io.Reader;

/**
 * 编辑面板
 */
@Getter
@Setter
@NoArgsConstructor
public class AdocTextPane extends JTextPane implements IFileBeanPath, ILineSpacingTextPane {
    /**
     * 不自动换行插件
     */
    @Delegate
    private NoLineWrapTextPanePlug plug = new NoLineWrapTextPanePlug(this);
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
     * 内容变更监听
     */
    private ChangeContextListener contentChanged;

    public AdocTextPane(StyledDocument doc) {
        super(doc);
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

    @Override
    public @NotNull MutableAttributeSet getInputAttributes() {
        if (inputStyle == null) {
            inputStyle = new FixedInputAttributeSet(this);
        }
        return inputStyle;
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
