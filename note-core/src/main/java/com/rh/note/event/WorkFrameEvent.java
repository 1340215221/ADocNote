package com.rh.note.event;

import com.rh.note.action.IOperationAction;
import com.rh.note.action.IWorkAction;
import com.rh.note.annotation.WorkSingleton;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.AWTEvent;

/**
 * 工作窗口 全局事件
 */
@WorkSingleton
public class WorkFrameEvent {

    @Autowired
    private IWorkAction workAction;
    @Autowired
    private IOperationAction operationAction;
    
    /**
     * 关闭项目前保存
     */
    public void save_all_text_pane() {
        workAction.saveAllEdited();
        workAction.promptToSaveSuccess();
    }

    /**
     * git 提交adoc内容
     */
    public void git_commit_adoc(AWTEvent event) {
        if(operationAction.checkIsCommitHotKey(event)) {
            workAction.saveAllEdited();
            workAction.commit();
        }
    }

    /**
     * 保存已打开文件内容
     */
    public void save_all_edited(AWTEvent event) {
        if(operationAction.checkIsSaveHotKey(event)) {
            workAction.saveAllEdited();
            workAction.loadTitleTree();
        }
    }

    /**
     * 关闭主窗口
     */
    public void close_frame() {
        workAction.closeFrame();
    }
}
