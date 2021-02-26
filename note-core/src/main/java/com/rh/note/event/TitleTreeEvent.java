package com.rh.note.event;

import com.rh.note.action.OperationAction;
import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.KeyEvent;

/**
 * 标题树 事件
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class TitleTreeEvent {

    @Autowired
    private OperationAction operationAction;
    @Autowired
    private WorkAction workAction;

    /**
     * 点击树节点
     */
    public void clicked_title_node() {
        workAction.openAdocFileByFilePath();
    }

    /**
     * 加载标题树根节点
     */
    public void load_root_node() {
        workAction.loadRootNode();
    }

    /**
     * 展开被选择标题级别的节点
     */
    public void expand_level_of_selected_node(KeyEvent event) {
        if (operationAction.isCtrl1(event)) {
            workAction.expandLevelOfSelectedNode();
        }
    }

    /**
     * 展开指定级别的节点
     */
    public void expand_designated_level(KeyEvent event) {
    }
}
