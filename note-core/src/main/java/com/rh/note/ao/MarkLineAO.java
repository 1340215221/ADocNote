package com.rh.note.ao;

import org.apache.commons.lang3.StringUtils;

import java.awt.event.KeyEvent;

/**
 * 标记行 1 参数
 */
public class MarkLineAO {
    /**
     * 行号
     */
    private Integer lineNumber;
    /**
     * 快捷键中的数字
     */
    private Integer keyMapNumber;
    /**
     * 来源编辑区文件路路径
     */
    private String sourceFilePath;
    /**
     * include文件路径
     */
    private String includeFilePath;

    public boolean checkRequiredItems() {
        return lineNumber == null || keyMapNumber == null || keyMapNumber != 1 && keyMapNumber !=2
                || StringUtils.isBlank(sourceFilePath) || StringUtils.isBlank(includeFilePath);
    }

    public MarkLineAO setKeyMapNumber(Integer keyMapNumber) {
        this.keyMapNumber = keyMapNumber;
        return this;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public MarkLineAO setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
        return this;
    }

    public String getIncludeFilePath() {
        return includeFilePath;
    }

    public MarkLineAO setIncludeFilePath(String includeFilePath) {
        this.includeFilePath = includeFilePath;
        return this;
    }

    public Integer getKeyMapNumber() {
        return keyMapNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public MarkLineAO setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public void copy(KeyEvent event) {
        if (event == null) {
            return;
        }
        if (event.getKeyCode() == 49 && event.getModifiers() == 2) {
            keyMapNumber = 1;
        }
        if (event.getKeyCode() == 50 && event.getModifiers() == 2) {
            keyMapNumber = 2;
        }
    }

    /**
     * 是快捷键 ctrl 1
     */
    public boolean isCtrlOne() {
        return keyMapNumber == 1;
    }

    /**
     * 是快捷键 ctrl 1
     */
    public boolean isCtrlTwo() {
        return keyMapNumber == 2;
    }
}
