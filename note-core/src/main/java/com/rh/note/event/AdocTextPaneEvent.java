package com.rh.note.event;

import com.rh.note.action.OperationAction;
import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.ao.TextPaneKeyStrokeAO;
import com.rh.note.constants.FrameCategoryEnum;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.MouseEvent;

@ComponentBean(FrameCategoryEnum.WORK)
public class AdocTextPaneEvent {

    @Autowired
    private OperationAction operationAction;
    @Autowired
    private WorkAction workAction;

    /**
     * ctrl左键点击
     */
    public void enter_include_file(MouseEvent event) {
        boolean isCtrlLeftClick = operationAction.isCtrlLeftClick(event);
        if (isCtrlLeftClick) {
            workAction.openIncludePointingAdocFileInSelectedTextPane();
        }
    }

    /**
     * 回车操作
     */
    public void enter_operation(@NonNull TextPaneKeyStrokeAO ao) {
        throw new RuntimeException();
    }

    /**
     * 选择上一个在提示框
     */
    public void select_previous_on_prompt(@NonNull TextPaneKeyStrokeAO ao) {
        throw new RuntimeException();
    }

    /**
     * 选择下一个在提示框
     */
    public void select_next_on_prompt(@NonNull TextPaneKeyStrokeAO ao) {
        throw new RuntimeException();
    }
}
