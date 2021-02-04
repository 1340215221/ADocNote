package com.rh.note.event;

import com.rh.note.action.ProManageAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 项目管理 事件
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
public class ProManageEvent {

    @Autowired
    private ProManageAction proManageAction;

    /**
     * 退出程序
     */
    public void exit_app() {
        proManageAction.exitApp();
    }
}
