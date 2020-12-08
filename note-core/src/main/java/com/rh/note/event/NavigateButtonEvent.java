package com.rh.note.event;


import com.rh.note.action.IOperationAction;
import com.rh.note.action.IWorkAction;
import com.rh.note.annotation.WorkSingleton;
import com.rh.note.vo.ITitleLineVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.MouseEvent;

/**
 * 导航按钮事件
 */
@WorkSingleton
public class NavigateButtonEvent {

    @Autowired
    private IOperationAction operationAction;
    @Autowired
    private IWorkAction workAction;

    /**
     * 点击导航按钮
     */
    public void clicked_navigate_button(MouseEvent event) {
        ITitleLineVO vo = operationAction.clickedNavigateButton(event);
        if (vo == null) {
            return;
        }
        workAction.openTextPaneByTitleNode(vo);
        workAction.positioningLineByTitle(vo);
        workAction.loadTitleNavigate(vo);
    }

}
