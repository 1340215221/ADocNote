package com.rh.note.event;


import com.rh.note.ao.ITitleContentAO;
import com.rh.note.ao.IncludeFilePathInfoAO;
import com.rh.note.ao.GenerateIncludeSyntaxAO;
import com.rh.note.ao.GenerateTitleSyntaxAO;
import com.rh.note.ao.InlineTitleAO;
import com.rh.note.ao.RenameIncludeAO;
import com.rh.note.vo.ITitleLineVO;
import groovy.swing.binding.JComboBoxMetaMethods;

import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

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
        ITitleLineVO vo = operationAction().getRootTitleOfCaretLineIncludeTargetFile(event);
        if (vo == null) {
            return;
        }
        workAction().openTextPaneByTitleNode(vo);
    }

    /**
     * 回车操作
     */
    public static void enter_operation(ActionEvent event) {
        // include快捷语法
        GenerateIncludeSyntaxAO includeAO = operationAction().selectCaretLineOfIncludeSyntaxSugar(event);
        if (includeAO != null) {
            workAction().generateIncludeSyntaxBySelectedText(includeAO);
        }
        // title快捷语法
        GenerateTitleSyntaxAO titleAO = operationAction().selectCaretLineOfTitleSyntaxSugar(event);
        if (titleAO != null) {
            workAction().generateTitleSyntaxBySelectedText(titleAO);
        }
        // 修改成功保存编辑区内容
        if (includeAO != null || titleAO != null) {
            workAction().saveAllEdited();
        }
        // 重新加载标题树
        if (includeAO != null || titleAO != null) {
            workAction().loadTitleTree();
            return;
        }
        // 默认回车操作
        workAction().defaultEnterOperation(event);
    }

    /**
     * include重命名
     */
    public static void renameInclude(KeyEvent event) {
        RenameIncludeAO ao = operationAction().renameIncludeOperation(event);
        if (ao == null) {
            return;
        }
        // 修改include名
        workAction().renameInclude(ao);
        // 重新加载标题
        workAction().loadTitleTree();
    }

    /**
     * 内联标题
     */
    public static void inline_title(KeyEvent event) {
        InlineTitleAO ao = operationAction().inlineTitleOperation(event);
        if (ao == null) {
            return;
        }
        workAction().saveAllEdited();
        workAction().inlineTitle(ao);
        workAction().saveAllEdited();
        workAction().loadTitleTree();
        ITitleLineVO titleLineVO = operationAction().getTitleByCaretLineContent();
        if (titleLineVO != null) {
            workAction().loadTitleNavigate(titleLineVO);
        }
    }

    /**
     * 下沉标题
     */
    public static void sink_title(KeyEvent event) {
        ITitleContentAO ao = operationAction().sinkTitleOperation(event);
        if (ao == null) {
            return;
        }
        workAction().saveAllEdited();
        workAction().sinkTitle(ao);
        workAction().saveAllEdited();
        workAction().loadTitleTree();
        ITitleLineVO vo = operationAction().getTitleByCaretLineContent();
        if (vo != null) {
            workAction().loadTitleNavigate(vo);
        }
    }

    /**
     * 输入提示下拉
     */
    public static void inputPromptDropDown(KeyEvent event) {
    }

    /**
     * 安全删除include行
     */
    public static void delete_include(KeyEvent event) {
        IncludeFilePathInfoAO ao = operationAction().deleteIncludeOperation(event);
        if (ao == null) {
            return;
        }
        workAction().deleteIncludeOnCaretLine(ao);
        workAction().saveAllEdited();
        workAction().loadTitleTree();
    }

    /**
     * 移动光标事件
     */
    public static void move_caret() {
        ITitleLineVO vo = operationAction().getTitleByCaretLineContent();
        if (vo == null) {
            return;
        }
        workAction().loadTitleNavigate(vo);
    }
}
