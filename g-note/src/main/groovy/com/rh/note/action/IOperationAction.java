package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.vo.ITitleLineVO;
import org.jetbrains.annotations.NotNull;

import java.awt.event.MouseEvent;

/**
 * 解析用户操作--入口
 */
public interface IOperationAction {
    /**
     * 点击历史项目
     */
    ClickedHistoryProjectListAO clickedHistoryProjectList(@NotNull MouseEvent mouseEvent);

    /**
     * 获得选择的标题节点
     */
    ITitleLineVO getSelectedTitleNode();

    /**
     * 获得选择的编辑区
     */
    ITitleLineVO getSelectedTextPane();

    /**
     * 点击导航按钮
     */
    ITitleLineVO clickedNavigateButton(MouseEvent event);

    /**
     * 判断是否为文件根标题
     */
    boolean checkIsFileRootTitle(ITitleLineVO vo);
}
