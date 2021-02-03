package com.rh.note.event;

import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 工作窗口 事件
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class WorkFrameEvent {

    @Autowired
    private WorkAction workAction;

    /**
     * 关闭当前编辑区
     */
    public void closeCurrentTextPane() {
        workAction.closeCurrentTextPane();
    }

    /**
     * 保存所有编辑区内容
     */
    public void save_all_edit_text() {
        // 保存编辑区所有文件
        workAction.saveAllTextPaneFile();
        // 更新标题树
        workAction.loadRootNode();
    }

    /**
     * 保存所有的编辑区
     */
    public void save_all_text_pane() {
        // todo
    }

    /**
     * 关闭窗口
     */
    public void close_frame() {
        workAction.closeContext();
    }

    /**
     * 关闭其他编辑区
     */
    public void closeOtherTextPane() {
        workAction.closeTextPaneNotSelected();
    }
}
