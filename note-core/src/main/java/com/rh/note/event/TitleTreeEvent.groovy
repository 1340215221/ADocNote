package com.rh.note.event

import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseEvent
import com.rh.note.constants.FrameCategoryEnum

/**
 * 标题树 事件
 */
@ComponentBean(FrameCategoryEnum.WORK)
class TitleTreeEvent extends BaseEvent {

    /**
     * 点击树节点
     */
    void clicked_title_node() {
        //todo
    }
}
