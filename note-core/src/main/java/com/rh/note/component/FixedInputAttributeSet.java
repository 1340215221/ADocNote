package com.rh.note.component;

import lombok.experimental.Delegate;

import javax.swing.plaf.FontUIResource;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.Enumeration;

/**
 * 固定的输入样式
 */
public class FixedInputAttributeSet implements MutableAttributeSet {

    @Delegate(excludes = ExcludeInterface.class)
    private final MutableAttributeSet inputStyle = new SimpleAttributeSet();

    public FixedInputAttributeSet(FixedInputAttributeSet style) {
        if (style != null) {
            inputStyle.addAttributes(style.inputStyle);
        }
    }

    public FixedInputAttributeSet(AdocTextPane textPane) {
        if (textPane == null) {
            return;
        }
        Font font = textPane.getFont();
        if (font != null && !(font instanceof FontUIResource)) {
            StyleConstants.setFontFamily(inputStyle, font.getFamily());
            StyleConstants.setFontSize(inputStyle, font.getSize());
        }
    }

    @Override
    public void addAttribute(Object name, Object value) {}

    @Override
    public void addAttributes(AttributeSet attributes) {}

    @Override
    public void removeAttribute(Object name) {}

    @Override
    public void removeAttributes(Enumeration<?> names) {}

    @Override
    public void removeAttributes(AttributeSet attributes) {}

    @Override
    public Object clone() {
        return new FixedInputAttributeSet(this);
    }

    interface ExcludeInterface {
        void addAttribute(Object name, Object value);
        void addAttributes(AttributeSet attributes);
        void removeAttribute(Object name);
        void removeAttributes(Enumeration<?> names);
        void removeAttributes(AttributeSet attributes);
    }
}
