package com.rh.note.action;

import com.rh.note.vo.ITitleLineVO;

/**
 * 工作窗口--入口
 */
public interface IWorkAction {
    /**
     * 打开编辑区, 通过标题节点
     */
    void openTextPaneByTitleNode(ITitleLineVO vo);

    /**
     * 加载标题导航
     */
    void loadTitleNavigate(ITitleLineVO vo);
}
