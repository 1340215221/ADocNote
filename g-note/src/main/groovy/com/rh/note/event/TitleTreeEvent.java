package com.rh.note.event;

import com.rh.note.bean.IAdocFile;

import static com.rh.note.config.BridgingBeanConfig.workAction;
import static com.rh.note.config.BridgingBeanConfig.operationAction;

/**
 * 标题树 事件
 */
public class TitleTreeEvent {

    /**
     * 点击标题树节点
     */
    public static void clicked_title_node() {
        IAdocFile adocFile = operationAction().clickedTitleTreeNode();
        if (adocFile == null) {
            return;
        }
        workAction().openTextPaneByAdocFile(adocFile);
        workAction().loadTitleNavigateByTitle(adocFile.getRootTitle());
    }

}
