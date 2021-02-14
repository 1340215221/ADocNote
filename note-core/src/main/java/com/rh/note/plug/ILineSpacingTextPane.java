package com.rh.note.plug;

import javax.swing.*;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * 可设置行间距编辑区
 */
public interface ILineSpacingTextPane {

    /**
     * 设置行间距样式
     */
    default void initLineSpacing() {
        if (!(this instanceof JTextPane)) {
            return;
        }
        JTextPane textPane = (JTextPane) this;
        initLineSpacing(textPane.getStyledDocument());
    }

    /**
     * 设置行间距样式
     */
    default void initLineSpacing(StyledDocument document) {
        if (getLineSpacing() == null || getLineSpacing() <= 0f || document == null) {
            return;
        }
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setLineSpacing(set, getLineSpacing());
        Element element = document.getDefaultRootElement();
        document.setParagraphAttributes(element.getStartOffset(), element.getEndOffset(), set, false);
    }

    Float getLineSpacing();
}
