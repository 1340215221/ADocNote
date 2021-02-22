package com.rh.note.event;

import com.rh.note.action.OperationAction;
import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.ao.OpenIncludePointingAdocFileAO;
import com.rh.note.ao.OpenIncludePointingReadOnlyFileAO;
import com.rh.note.ao.TextPaneKeyStrokeAO;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.timer.RefreshSyntaxHighlightOfAdocTextPaneSelectedTimer;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

@ComponentBean(FrameCategoryEnum.WORK)
public class AdocTextPaneEvent {

    @Autowired
    RefreshSyntaxHighlightOfAdocTextPaneSelectedTimer timer;
    @Autowired
    private OperationAction operationAction;
    @Autowired
    private WorkAction workAction;

    /**
     * 立即执行一个语法高亮定时任务
     */
    public void refresh_syntax_highlight_by_timer(String filePath) {
        timer.cancelOnceRun();
        workAction.refreshSyntaxHighlightOfAdocTextPaneByFilePath(filePath);
    }

    /**
     * ctrl左键点击
     */
    public void enter_include_file(MouseEvent event) {
        if (operationAction.isCtrlLeftClick(event)) {
            workAction.openIncludePointingFileInSelectedTextPane(new OpenIncludePointingAdocFileAO());
        }
    }

    /**
     * 进入只读文件
     */
    public void enter_include_read_only_file(MouseEvent event) {
        if (operationAction.isCtrlLeftClick(event)) {
            workAction.openIncludePointingFileInSelectedTextPane(new OpenIncludePointingReadOnlyFileAO());
        }
    }

    /**
     * 回车输入 替换
     */
    public void enter_input() {
        workAction.handleSyntaxSugarByCaretLineOfSelectedTextPane();
        workAction.loadRootNode();
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

    /**
     * 重命名include指向标题名
     */
    public void rename_include(KeyEvent event) {
        if (operationAction.isShiftF6(event)) {
            workAction.renameIncludeOnCaretLineOfTextPaneSelected();
        }
    }

    /**
     * 删除include语法
     */
    public void delete_include(KeyEvent event) {
        if (operationAction.isAltDel(event)) {
            workAction.deleteIncludeOnCaretLineOfTextPaneSelected();
            workAction.loadRootNode();
        }
    }
}
