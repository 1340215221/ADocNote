package com.rh.note.event;

import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 工作窗口 事件
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class WorkFrameEvent {

    @Autowired
    private WorkAction workAction;

    /**
     * 关闭窗口
     */
    public void close_frame() {
        workAction.saveAllTextPaneFile();
        workAction.closeContext();
    }
}
