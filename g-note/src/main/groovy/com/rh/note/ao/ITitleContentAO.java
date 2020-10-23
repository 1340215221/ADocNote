package com.rh.note.ao;

/**
 * 标题内容 参数
 */
public interface ITitleContentAO {
    /**
     * 获得文件路径
     */
    String getFilePath();

    /**
     * 获得生成文件参数
     */
    <T extends ICreateFileAndInitContentAO> T getCreateFileAO();

    /**
     * 获得include行内容
     */
    String getIncludeLineText();
}
