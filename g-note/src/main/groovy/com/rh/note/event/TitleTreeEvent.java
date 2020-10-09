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
        // 打开标题节点对应的文件
        workAction().openTextPaneByTitleNode(vo);
        // 光标定位到标题所在行, 文件根节点情况除外
        workAction().positioningLineByTitle(vo);
        // 加载标题导航
        ITitleLineVO vo2 = operationAction().getTitleByCaretLineContent();
        if (vo2 != null) {
            workAction().loadTitleNavigate(vo2);
        }
    }

}
