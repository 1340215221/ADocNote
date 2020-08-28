package com.rh.note.action;

import java.awt.event.ActionEvent;

public interface IWorkAction {
    /**
     * 判断当前行是否为include语法
     */
    boolean isIncludeGrammarLine(String componentId);
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

    /**
     * 删除include行
     */
    void deleteInclude(ActionEvent e);

    /**
     * 输入回车
     */
    void insertEnter(ActionEvent e);

    /**
     * 生成表格块
     */
    void generateTableBlock(String s);
}
