package com.rh.note.component;

import cn.hutool.core.util.ReflectUtil;
import com.rh.note.common.IFileBeanPath;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.path.FileBeanPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.text.*;
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
        super.read(reader, desc);
        initLineSpacing();
    }

    public void setLineSpacing(Float lineSpacing) {
        this.lineSpacing = lineSpacing;
        initLineSpacing();
    }

    /**
     * 设置行间距样式
     */
    private void initLineSpacing() {
        if (lineSpacing == null || lineSpacing == 0f) {
            return;
        }
        try {
            StyledDocument document = getStyledDocument();
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
