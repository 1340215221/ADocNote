package com.rh.note.ao;

import com.rh.note.exception.RequestParamsValidException;
import org.apache.commons.lang3.StringUtils;

/**
 * 内联标题
 */
public class InlineTitleAO {

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * include指向文件路径
     */
    private String targetFilePath;
    /**
     * include指向文件内容
     */
    private String targetFileContent;

    public String getFilePath() {
        return filePath;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }

    public String getTargetFileContent() {
        return targetFileContent;
    }

    public InlineTitleAO setTargetFileContent(String targetFileContent) {
        this.targetFileContent = targetFileContent;
        return this;
    }

    /**
     * 复制include中的文件地址信息
     */
    public void copy(IncludeFilePathInfoAO filePathAO) {
        if (filePathAO == null) {
            return;
        }

        filePath = filePathAO.getFilePath();
        targetFilePath = filePathAO.getTargetFilePath();
    }

    /**
     * 检查参数
     */
    public void checkRequiredItems() {
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(targetFilePath) || StringUtils.isBlank(targetFileContent)) {
            throw new RequestParamsValidException();
        }
    }

    /**
     * 检查参数
     */
    public boolean isErrorParams() {
        return StringUtils.isBlank(filePath) || StringUtils.isBlank(targetFilePath) || StringUtils.isBlank(targetFileContent);
    }
}
