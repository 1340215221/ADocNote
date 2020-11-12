package com.rh.note.ao;

/**
 * 指向文件路径, 通过include java行
 */
public class TargetFilePathByIncludeJavaLineAO {
    /**
     * 文件绝对路径
     */
    private String absolutePath;
    /**
     * 来源编辑区文件路径
     */
    private String sourceFilePath;
    /**
     * include java中的文件路径
     */
    private String includeFilePath;
    /**
     * 标记行 1
     */
    private Integer markLineNumber1;
    /**
     * 标记行 2
     */
    private Integer markLineNumber2;

    public Integer getMarkLineNumber1() {
        return markLineNumber1;
    }

    public TargetFilePathByIncludeJavaLineAO setMarkLineNumber1(Integer markLineNumber1) {
        this.markLineNumber1 = markLineNumber1;
        return this;
    }

    public Integer getMarkLineNumber2() {
        return markLineNumber2;
    }

    public TargetFilePathByIncludeJavaLineAO setMarkLineNumber2(Integer markLineNumber2) {
        this.markLineNumber2 = markLineNumber2;
        return this;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public TargetFilePathByIncludeJavaLineAO setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
        return this;
    }

    public String getIncludeFilePath() {
        return includeFilePath;
    }

    public TargetFilePathByIncludeJavaLineAO setIncludeFilePath(String includeFilePath) {
        this.includeFilePath = includeFilePath;
        return this;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public TargetFilePathByIncludeJavaLineAO setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        return this;
    }
}
