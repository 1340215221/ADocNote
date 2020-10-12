package com.rh.note.ao;

/**
 * 删除include 参数
 */
public class IncludeFilePathInfoAO {

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * include指向文件路径
     */
    private String targetFilePath;

    /**
     * 空白内容
     */
    public String getBlankText() {
        return "";
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    public IncludeFilePathInfoAO setTargetFilePath(String targetFilePath) {
        this.targetFilePath = targetFilePath;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public IncludeFilePathInfoAO setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }
}
