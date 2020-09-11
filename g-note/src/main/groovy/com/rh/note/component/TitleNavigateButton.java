package com.rh.note.component;

import com.rh.note.bean.ITitleLine;

import javax.swing.JButton;

/**
 * 标题导航按钮
 */
public class TitleNavigateButton extends JButton {

    /**
     * 标题
     */
    private ITitleLine titleLine;

    public void setTitleLine(ITitleLine titleLine) {
        this.titleLine = titleLine;
    }

    public ITitleLine getTitleLine() {
        return titleLine;
    }
}
