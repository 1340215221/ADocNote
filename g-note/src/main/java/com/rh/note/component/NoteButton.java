package com.rh.note.component;

import com.rh.note.grammar.ITitleGrammar;

import javax.swing.*;

/**
 * 自定义按钮
 */
public class NoteButton extends JButton {

    /**
     * 标题
     */
    private ITitleGrammar titleGrammar;

    public ITitleGrammar getTitleGrammar() {
        return titleGrammar;
    }

    public void setTitleGrammar(ITitleGrammar titleGrammar) {
        this.titleGrammar = titleGrammar;
    }
}
