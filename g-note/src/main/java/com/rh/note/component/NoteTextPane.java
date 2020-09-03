package com.rh.note.component;

import com.rh.note.grammar.ITitleGrammar;

import javax.swing.*;

/**
 * 自定义编辑区
 */
public class NoteTextPane extends JTextPane {

    /**
     * 根标题
     */
    private ITitleGrammar titleGrammar;

    public NoteTextPane(ITitleGrammar titleGrammar) {
        this.titleGrammar = titleGrammar;
    }

    public ITitleGrammar getTitleGrammar() {
        return titleGrammar;
    }

    public void setTitleGrammar(ITitleGrammar titleGrammar) {
        this.titleGrammar = titleGrammar;
    }

    /**
     * 获得编辑文件路径
     */
    public String getFilePath() {
        return titleGrammar.getFilePath();
    }

}
