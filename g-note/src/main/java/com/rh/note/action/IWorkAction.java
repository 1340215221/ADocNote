package com.rh.note.action;

public interface IWorkAction {
    /**
     * 生成include语法块
     */
    void generateIncludeBlock(String componentId) throws Exception;

    /**
     * include重命名
     */
    void rename(String componentId) throws Exception;

    /**
     * 打开标题内容
     */
    void openAdocFile();

    /**
     * 显示或隐藏标题列表
     */
    void hiddenOrShowTitleList();

    /**
     * 全局保存
     */
    void saveAllEditContent();
}
