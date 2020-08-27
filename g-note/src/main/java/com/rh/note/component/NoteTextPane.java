package com.rh.note.component;

import javax.swing.*;

/**
 * 自定义编辑区
 */
public class NoteTextPane extends JTextPane {

    /**
     * adoc对象
     */
    private String filePath;

    public NoteTextPane(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获得编辑文件路径
     */
    public String getFilePath() {
        return filePath;
    }

    public NoteTextPane setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

}
