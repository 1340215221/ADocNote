package com.rh.note.event;


import com.rh.note.vo.ITitleLineVO;

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
        ITitleLineVO vo = operationAction().getSelectedTitleNode();
        if (vo == null) {
            return;
        }
        workAction().openTextPaneByTitleNode(vo);
        workAction().loadTitleNavigate(vo);
        if(!operationAction().checkIsFileRootTitle(vo)) {
            workAction().positioningLineByTitle(vo);
        }
    }

}
