package com.rh.note.event;


import com.rh.note.vo.ITitleLineVO;

import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 文件标签栏 事件
 * {@link }
 */
public class TabbedPaneEvent {

    /**
     * 加载标题导航, 在选择文件标签时
     */
    public static void load_title_navigate_on_selected_tab() {
        ITitleLineVO vo = operationAction().getTitleByCaretLineContent();
        if (vo == null) {
            return;
        }
        workAction().loadTitleNavigate(vo);
    }

}
