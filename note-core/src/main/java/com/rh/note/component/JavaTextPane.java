package com.rh.note.component;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/**
 * java文件文本编辑器
 */
public class JavaTextPane extends JTextPane {
    /**
     * 文件绝对路径
     */
    private String absolutePath;
    /**
     * 标记行 1
     */
    private Integer markLineNumber1;
    /**
     * 标记行 2
     */
    private Integer markLineNumber2;
    /**
     * 来源编辑区信息
     */
    private final SourceTextPaneInfo sourceTextPaneInfo = new SourceTextPaneInfo();

    public JavaTextPane() {
    }

    public JavaTextPane(StyledDocument doc) {
        super(doc);
    }

    public void setIncludeFilePath(String includeFilePath) {
        sourceTextPaneInfo.includeFilePath = includeFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        sourceTextPaneInfo.sourceFilePath = sourceFilePath;
    }

    public String getIncludeFilePath() {
        return sourceTextPaneInfo.includeFilePath;
    }

    public String getSourceFilePath() {
        return sourceTextPaneInfo.sourceFilePath;
    }

    public JavaTextPane setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        return this;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public Integer getMarkLineNumber1() {
        return markLineNumber1;
    }

    public void setMarkLineNumber1(Integer markLineNumber1) {
        this.markLineNumber1 = markLineNumber1;
    }

    public Integer getMarkLineNumber2() {
        return markLineNumber2;
    }

    public void setMarkLineNumber2(Integer markLineNumber2) {
        this.markLineNumber2 = markLineNumber2;
    }

    /**
     * 来源信息
     */
    private class SourceTextPaneInfo {
        /**
         * 来源文件路径
         */
        private String sourceFilePath;
        /**
         * include java指向的文件路径
         */
        private String includeFilePath;

        public String getSourceFilePath() {
            return sourceFilePath;
        }

        public void setSourceFilePath(String sourceFilePath) {
            this.sourceFilePath = sourceFilePath;
        }

        public String getIncludeFilePath() {
            return includeFilePath;
        }

        public void setIncludeFilePath(String includeFilePath) {
            this.includeFilePath = includeFilePath;
        }
    }
}
