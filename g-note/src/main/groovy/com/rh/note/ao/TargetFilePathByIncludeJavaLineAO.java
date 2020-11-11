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
