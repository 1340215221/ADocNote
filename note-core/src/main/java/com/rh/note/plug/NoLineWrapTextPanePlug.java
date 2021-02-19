package com.rh.note.plug;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.JTextPane;
import java.awt.Dimension;

/**
 * 不自动换行
 */
@RequiredArgsConstructor
public class NoLineWrapTextPanePlug {

    @NonNull
    private JTextPane textPane;

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public void setSize(Dimension d) {
        if (d.width < textPane.getParent().getSize().width) {
            d.width = textPane.getParent().getSize().width;
        }
        d.width += 100;
        textPane.setSize(d.width, d.height);
    }
}
