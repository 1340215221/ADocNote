package com.rh.note.event;

import com.rh.note.bean.ITitleLine;
import com.rh.note.component.AdocTextPane;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static com.rh.note.config.BridgingBeanConfig.defaultEventAction;
import static com.rh.note.config.BridgingBeanConfig.operationAction;
import static com.rh.note.config.BridgingBeanConfig.workAction;

/**
 * 编辑区事件
 */
public class TextPaneEvent {

    /**
     * 进入include指向文件
     */
    public static void enter_include_file(MouseEvent event) {
        if (event.getModifiers() == 18) {
            workAction().openIncludeFile();
        }
    }

    /**
     * 回车操作
     */
    public static void enter_operation(ActionEvent event) {
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane)) {
            return;
        }
        if (operationAction().matchGenerateIncludeBlock(((AdocTextPane) source).getAdocFile())) {
            workAction().generateIncludeBlock(((AdocTextPane) source).getAdocFile());
            return;
        }
        if (operationAction().matchGenerateTableBlock(((AdocTextPane) source).getAdocFile())) {
            workAction().generateTableBlock(((AdocTextPane) source).getAdocFile());
            return;
        }
        defaultEventAction().enter(event);
    }

    /**
     * include重命名
     */
    public static void rename(KeyEvent event) {
        if (event.getKeyCode() != 117 || event.getModifiers() != 1) {
            return;
        }
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane) || !operationAction().matchRename()) {
            return;
        }
        workAction().rename(((AdocTextPane) source).getAdocFile());
    }

    /**
     * 下沉标题
     */
    public static void sink_title(KeyEvent event) {
        if (event.getKeyCode() != 117 || event.getModifiers() != 0) {
            return;
        }
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane) || !operationAction().matchSinkTitle()) {
            return;
        }
        workAction().sinkTitle(((AdocTextPane) source).getAdocFile());
    }

    /**
     * 安全删除include行
     */
    public static void delete_include(ActionEvent event) {
        Object source = event.getSource();
        if (!operationAction().matchDeleteInclude() || !(source instanceof AdocTextPane)) {
            defaultEventAction().ctrlDelete(event);
            return;
        }
        workAction().deleteInclude(((AdocTextPane) source).getAdocFile());
    }

    /**
     * 移动光标时间
     */
    public static void move_caret() {
        ITitleLine titleLine = workAction().getCursorTitleOfSelectedTab();
        if (titleLine == null) {
            return;
        }
        workAction().loadTitleNavigateByTitle(titleLine);
    }
}
