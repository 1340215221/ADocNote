package com.rh.note.event;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 编辑区事件
 */
public class TextPaneEvent {

    /**
     * 进入include指向文件
     */
    public static void enter_include_file(MouseEvent event) {
    }

    /**
     * 回车操作
     */
    public static void enter_operation(ActionEvent event) {
    }

    /**
     * include重命名
     */
    public static void rename(KeyEvent event) {
    }

    /**
     * 下沉标题
     */
    public static void sink_title(KeyEvent event) {
    }

    /**
     * 安全删除include行
     */
    public static void delete_include(ActionEvent event) {
    }

    /**
     * 移动光标时间
     */
    public static void move_caret() {
    }
}
