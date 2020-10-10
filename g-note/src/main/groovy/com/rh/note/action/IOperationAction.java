package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.ao.GenerateIncludeSyntaxAO;
import com.rh.note.vo.ITitleLineVO;
import org.jetbrains.annotations.NotNull;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
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
     * 点击导航按钮
     */
    ITitleLineVO clickedNavigateButton(MouseEvent event);

    /**
     * 获得标题, 通过光标所在行内容
     */
    ITitleLineVO getTitleByCaretLineContent();

    /**
     * 是否为保存快捷键
     */
    boolean checkIsSaveHotKey(AWTEvent event);

    /**
     * 选择光标行, 如果是include语法糖
     */
    GenerateIncludeSyntaxAO selectCaretLineOfIncludeSyntaxSugar(ActionEvent event);
}
