package com.rh.note.event;

import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 标题树 事件
 */
public class TitleTreeEvent {

    /**
     * 点击标题树节点
     */
    public static void clicked_title_node() {
        workAction().openAdocFileBySelectedNode();
    }

}
