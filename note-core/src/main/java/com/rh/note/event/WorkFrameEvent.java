package com.rh.note.event;

import com.rh.note.action.OperationAction;
import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.ao.SaveTextPaneFileByFilePathAO;
import com.rh.note.constants.FrameCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 工作窗口 事件
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class WorkFrameEvent {

    @Autowired
    private OperationAction operationAction;
    @Autowired
    private WorkAction workAction;

    /**
     * 保存所有编辑区内容
     */
    public void save_all_edit_text() {
        SaveTextPaneFileByFilePathAO ao = operationAction.hasTextPaneOpened();
        if (ao == null) {
            return;
        }
        workAction.saveTextPaneFileByFilePaths(ao);
    }

    /**
     * 保存所有的编辑区
     */
    public void save_all_text_pane() {
        // todo
    }

    /**
     * 关闭窗口
     */
    public void close_frame() {
        workAction.closeContext();
    }
}
