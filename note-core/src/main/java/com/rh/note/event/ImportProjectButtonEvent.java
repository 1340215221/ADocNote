package com.rh.note.event;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.common.BaseEvent;
import com.rh.note.constants.FrameCategoryEnum;

/**
 * 项目管理--导入项目按钮 事件
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
public class ImportProjectButtonEvent extends BaseEvent {
    /**
     * 点击导入项目按钮
     */
    public void clicked_import_project_button() {
        //todo
    }
}
