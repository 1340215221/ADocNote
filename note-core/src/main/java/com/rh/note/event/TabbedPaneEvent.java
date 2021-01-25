package com.rh.note.event;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.common.BaseEvent;
import com.rh.note.constants.FrameCategoryEnum;

@ComponentBean(FrameCategoryEnum.WORK)
public class TabbedPaneEvent extends BaseEvent {

    /**
     * 加载标题导航, 通过被选择的标签
     */
    public void load_title_navigate_on_selected_tab() {
        // todo
    }
}
