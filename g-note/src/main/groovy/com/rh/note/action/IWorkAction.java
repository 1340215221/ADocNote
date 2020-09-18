package com.rh.note.action;

import com.rh.note.ao.ISyntaxAnalysisAO;
import com.rh.note.bean.IAdocFile;
import com.rh.note.bean.IBaseLine;
import com.rh.note.bean.IIncludeLine;
import com.rh.note.bean.ITitleLine;

import java.awt.event.ActionEvent;

/**
 * 工作窗口入口
 * A adocFile实现类型
 * T title语法实现类型
 */
public interface IWorkAction<A extends IAdocFile, T extends ITitleLine, I extends IIncludeLine, B extends IBaseLine,
        S extends ISyntaxAnalysisAO> {
    /**
     * include重命名
     */
    void rename(A adocFile);

    /**
     * 下沉标题
     */
    void sinkTitle(A adocFile);

    /**
     * 安全删除include
     */
    void deleteInclude(A adocFile);

    /**
     * 加载标题导航, 在选择文件标签时
     */
    void loadTitleNavigateByTitle(T titleLine);

    /**
     * 保存所有打开编辑区的内容
     */
    void saveAllEdited();

    /**
     * 获得被选择的adoc文件光标所在标题
     */
    T getCursorTitleOfSelectedTab();

    /**
     * 打开编辑区,并定位标题
     */
    void openTextPaneByTitle(T titleLine);

    /**
     * 打开编辑区, 通过include对象
     */
    void openTextPaneByInclude(I includeLine);

    /**
     * 生成include块
     * @param ao
     */
    void generateIncludeBlock(S ao);

    /**
     * 插入回车
     */
    void insertEnter(ActionEvent event, IBaseLine operationLine);
}
