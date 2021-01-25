package com.rh.note.event;

import com.rh.note.action.OperationAction;
import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.common.BaseEvent;
import com.rh.note.constants.FrameCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 标题树 事件
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class TitleTreeEvent extends BaseEvent {

    @Autowired
    private OperationAction operationAction;
    @Autowired
    private WorkAction workAction;

    /**
     * 点击树节点
     */
    public void clicked_title_node() {
        //todo
    }

    /**
     * 加载标题树根节点
     */
    public void load_root_node() {
        workAction.loadRootNode();
    }
}
