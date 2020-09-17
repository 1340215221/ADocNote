package com.rh.note.action;

import com.rh.note.ao.ISyntaxAnalysisAO;
import com.rh.note.bean.IAdocFile;
import com.rh.note.bean.IBaseLine;
import com.rh.note.bean.IIncludeLine;
import com.rh.note.bean.ITitleLine;

/**
 * 解析用户操作入口
 */
public interface IOperationAction<A extends IAdocFile, T extends ITitleLine, I extends IIncludeLine, B extends IBaseLine,
        S extends ISyntaxAnalysisAO> {
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
     * 点击历史项目列表
     */
    String clickedHistoryProjectList();

    /**
     * 点击标题树节点
     */
    T clickedTitleTreeNode();

    /**
     * 点击include行
     */
    I clickedIncludeLine();

    /**
     * 回车操作
     */
    S enterOperation();
}
