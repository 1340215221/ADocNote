package com.rh.note.component;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/**
 * java文件文本编辑器
 */
public class JavaTextPane extends JTextPane {

    public static final String NAME = "jTextPane";
    /**
     * 文件绝对路径
     */
    private String absolutePath;

    public JavaTextPane() {
    }

    public JavaTextPane(StyledDocument doc) {
        super(doc);
    }

    public JavaTextPane setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        return this;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}
