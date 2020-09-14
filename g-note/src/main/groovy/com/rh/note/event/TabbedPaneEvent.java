package com.rh.note.event;

import com.rh.note.bean.ITitleLine;

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
        ITitleLine titleLine = workAction().getRootTitleOfSelectedTab();
        if (titleLine == null) {
            return;
        }
        workAction().loadTitleNavigateOnSelectedTab(titleLine);
    }

}
