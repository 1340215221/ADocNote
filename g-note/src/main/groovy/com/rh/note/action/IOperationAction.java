package com.rh.note.action;

import com.rh.note.bean.IAdocFile;

/**
 * 解析用户操作入口
 */
public interface IOperationAction<A extends IAdocFile> {
    /**
     * 匹配 include重命名操作
     */
    boolean matchRename();
    /**
     * 匹配 下沉标题操作
     */
    boolean matchSinkTitle();
    /**
     * 匹配 安全删除include
     */
    boolean matchDeleteInclude();

    /**
     * 匹配 生成include块操作
     */
    boolean matchGenerateIncludeBlock(IAdocFile adocFile);

    /**
     * 匹配 生成table块操作
     */
    boolean matchGenerateTableBlock(IAdocFile adocFile);

    /**
     * 点击历史项目列表
     */
    String clickedHistoryProjectList();

    A clickedTitleTreeNode();
}
