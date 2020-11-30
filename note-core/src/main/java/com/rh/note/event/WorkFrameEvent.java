package com.rh.note.event;

import java.awt.AWTEvent;

import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 工作窗口 全局事件
 */
public class WorkFrameEvent {

    /**
     * 关闭项目前保存
     */
    public static void save_all_text_pane() {
        workAction().saveAllEdited();
        workAction().promptToSaveSuccess();
    }

    /**
     * git 提交adoc内容
     */
    public static void git_commit_adoc(AWTEvent event) {
        if(operationAction().checkIsCommitHotKey(event)) {
            workAction().saveAllEdited();
            workAction().commit();
        }
    }

    /**
     * 保存已打开文件内容
     */
    public static void save_all_edited(AWTEvent event) {
        if(operationAction().checkIsSaveHotKey(event)) {
            workAction().saveAllEdited();
            workAction().loadTitleTree();
        }
    }

    /**
     * 关闭主窗口
     */
    public static void close_frame() {
        workAction().closeFrame();
    }
}
