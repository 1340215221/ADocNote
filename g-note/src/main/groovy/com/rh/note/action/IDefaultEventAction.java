package com.rh.note.action;

import java.awt.event.ActionEvent;

/**
 * 默认事件操作入口
 */
public interface IDefaultEventAction {

    /**
     * ctrl + del
     */
    void ctrlDelete(ActionEvent event);

    /**
     * enter
     */
    void enter(ActionEvent event);
}
