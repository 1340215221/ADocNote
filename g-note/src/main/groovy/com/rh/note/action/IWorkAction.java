package com.rh.note.action;

import com.rh.note.bean.IAdocFile;
import com.rh.note.bean.ITitleLine;

/**
 * 工作窗口入口
 * A adocFile实现类型
 * T title语法实现类型
 */
public interface IWorkAction<A extends IAdocFile, T extends ITitleLine> {
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
     * 生成include块
     */
    void generateIncludeBlock(A adocFile);

    /**
     * 生成table块
     */
    void generateTableBlock(A adocFile);

    /**
     * 在编辑区打开adoc文件
     */
    void openTextPaneByAdocFile(A adocFile);

    /**
     * 保存所有打开编辑区的内容
     */
    void saveAllEdited();

    /**
     * 打开光标所在include指向的文件
     */
    void openIncludeFile();

    /**
     * 获得被选择的adoc文件对象
     */
    T getRootTitleOfSelectedTab();

    /**
     * 打开编辑区,并定位标题
     */
    void openTextPaneByTitle(T titleLine);
}
