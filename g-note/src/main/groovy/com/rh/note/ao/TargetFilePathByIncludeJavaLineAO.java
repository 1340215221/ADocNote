package com.rh.note.ao;

/**
 * 指向文件路径, 通过include java行
 */
public class TargetFilePathByIncludeJavaLineAO {
    /**
     * 文件绝对路径
     */
    private String absolutePath;

    public String getAbsolutePath() {
        return absolutePath;
    }

    public TargetFilePathByIncludeJavaLineAO setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        return this;
    }
}
