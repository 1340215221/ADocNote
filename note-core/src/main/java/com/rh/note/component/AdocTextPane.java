package com.rh.note.component;

import cn.hutool.core.util.ReflectUtil;
import com.rh.note.common.IFileBeanPath;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.path.FileBeanPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Method;

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
        initLineSpacing(doc); // 设置行间距样式
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
    private void initLineSpacing(Document document) {
        if (lineSpacing == null || lineSpacing == 0f || document == null) {
            return;
        }
        try {
            MutableAttributeSet set = (MutableAttributeSet) document.getDefaultRootElement().getAttributes();
            Method method = AbstractDocument.class.getDeclaredMethod("getAttributeContext");
            method.setAccessible(true);
            AbstractDocument.AttributeContext context = (AbstractDocument.AttributeContext) method.invoke(document);
            AttributeSet attributes = (AttributeSet) ReflectUtil.getFieldValue(set, "attributes");
            attributes = context.addAttribute(attributes, StyleConstants.LineSpacing, lineSpacing);
            ReflectUtil.setFieldValue(set, "attributes", attributes);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_SET_TEXT_PANE_LINE_NUMBER, e);
        }
    }
}
