package com.rh.note.event;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.ao.TextPaneKeyStrokeAO;
import com.rh.note.common.BaseEvent;
import com.rh.note.constants.FrameCategoryEnum;
import lombok.NonNull;

@ComponentBean(FrameCategoryEnum.WORK)
public class AdocTextPaneEvent extends BaseEvent {

    /**
     * 回车操作
     */
    public void enter_operation(@NonNull TextPaneKeyStrokeAO ao) {
    }

    /**
     * 选择上一个在提示框
     */
    public void select_previous_on_prompt(@NonNull TextPaneKeyStrokeAO ao) {
    }

    /**
     * 选择下一个在提示框
     */
    public void select_next_on_prompt(@NonNull TextPaneKeyStrokeAO ao) {
    }
}
