package com.rh.note.event;

import com.rh.note.bean.ITitleLine;

import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 标题树 事件
 */
public class TitleTreeEvent {

    /**
     * 点击标题树节点
     */
    public static void clicked_title_node() {
        ITitleLine titleLine = operationAction().clickedTitleTreeNode();
        if (titleLine == null) {
            return;
        }
        workAction().openTextPaneByTitle(titleLine);
    }

}
