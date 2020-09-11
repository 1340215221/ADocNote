package com.rh.note.event;

import com.rh.note.component.AdocTextPane;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static com.rh.note.config.BridgingBeanConfig.defaultEventAction;
import static com.rh.note.config.BridgingBeanConfig.matchAction;
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
        if (matchAction().matchGenerateIncludeBlock(((AdocTextPane) source).getAdocFile())) {
            workAction().generateIncludeBlock(((AdocTextPane) source).getAdocFile());
            return;
        }
        if (matchAction().matchGenerateTableBlock(((AdocTextPane) source).getAdocFile())) {
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
        if (!(source instanceof AdocTextPane) || !matchAction().matchRename()) {
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
        if (!(source instanceof AdocTextPane) || !matchAction().matchSinkTitle()) {
            return;
        }
        workAction().sinkTitle(((AdocTextPane) source).getAdocFile());
    }

    /**
     * 安全删除include行
     */
    public static void delete_include(ActionEvent event) {
        Object source = event.getSource();
        if (!matchAction().matchDeleteInclude() || !(source instanceof AdocTextPane)) {
            defaultEventAction().ctrlDelete(event);
            return;
        }
        workAction().deleteInclude(((AdocTextPane) source).getAdocFile());
    }
}
