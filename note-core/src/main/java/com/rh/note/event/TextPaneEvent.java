package com.rh.note.event;


import com.rh.note.action.IOperationAction;
import com.rh.note.action.IWorkAction;
import com.rh.note.annotation.WorkSingleton;
import com.rh.note.ao.GenerateIncludeSyntaxAO;
import com.rh.note.ao.GenerateJavaIncludeSyntaxAO;
import com.rh.note.ao.GenerateTitleSyntaxAO;
import com.rh.note.ao.ITitleContentAO;
import com.rh.note.ao.IncludeFilePathInfoAO;
import com.rh.note.ao.IncludePromptAO;
import com.rh.note.ao.InlineTitleAO;
import com.rh.note.ao.RenameIncludeAO;
import com.rh.note.ao.SelectPromptItemAO;
import com.rh.note.ao.TargetFilePathByIncludeJavaLineAO;
import com.rh.note.vo.ITitleLineVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * 编辑区事件
 */
@WorkSingleton
public class TextPaneEvent {

    @Autowired
    private IOperationAction operationAction;
    @Autowired
    private IWorkAction workAction;
    
    /**
     * 进入include java文件
     */
    public void enter_include_java_file(MouseEvent event) {
        TargetFilePathByIncludeJavaLineAO ao = operationAction.getTargetFilePathByIncludeJavaLine(event);
        if (ao == null) {
            return;
        }
        workAction.openJavaFile(ao);
    }

    /**
     * 进入include指向文件
     */
    public void enter_include_file(MouseEvent event) {
        ITitleLineVO vo = operationAction.getRootTitleOfCaretLineIncludeTargetFile(event);
        if (vo == null) {
            return;
        }
        workAction.openTextPaneByTitleNode(vo);
    }

    /**
     * 回车操作
     */
    public void enter_operation(ActionEvent event) {
        // include快捷语法
        GenerateIncludeSyntaxAO includeAO = operationAction.selectCaretLineOfIncludeSyntaxSugar(event);
        if (includeAO != null) {
            workAction.generateIncludeSyntaxBySelectedText(includeAO);
        }
        // title快捷语法
        GenerateTitleSyntaxAO titleAO = operationAction.selectCaretLineOfTitleSyntaxSugar(event);
        if (titleAO != null) {
            workAction.generateTitleSyntaxBySelectedText(titleAO);
        }
        // include java 快捷语法
        GenerateJavaIncludeSyntaxAO javaIncludeSyntaxAO = operationAction.selectCaretLineOfJavaIncludeSyntaxSugar(event);
        if (javaIncludeSyntaxAO != null) {
            workAction.generateJavaIncludeSyntaxBySelectedText(javaIncludeSyntaxAO);
        }
        // 修改成功保存编辑区内容
        if (includeAO != null || titleAO != null || javaIncludeSyntaxAO != null) {
            workAction.saveAllEdited();
        }
        // 重新加载标题树
        if (includeAO != null || titleAO != null || javaIncludeSyntaxAO != null) {
            workAction.loadTitleTree();
            return;
        }
        // 默认回车操作
        workAction.defaultEnterOperation(event);
    }

    /**
     * include重命名
     */
    public void rename_include(KeyEvent event) {
        RenameIncludeAO ao = operationAction.renameIncludeOperation(event);
        if (ao == null) {
            return;
        }
        // 修改include名
        workAction.renameInclude(ao);
        // 重新加载标题
        workAction.loadTitleTree();
    }

    /**
     * 内联标题
     */
    public void inline_title(KeyEvent event) {
        InlineTitleAO ao = operationAction.inlineTitleOperation(event);
        if (ao == null) {
            return;
        }
        workAction.saveAllEdited();
        workAction.inlineTitle(ao);
        workAction.saveAllEdited();
        workAction.loadTitleTree();
        ITitleLineVO titleLineVO = operationAction.getTitleByCaretLineContent();
        if (titleLineVO != null) {
            workAction.loadTitleNavigate(titleLineVO);
        }
    }

    /**
     * 下沉标题
     */
    public void sink_title(KeyEvent event) {
        ITitleContentAO ao = operationAction.sinkTitleOperation(event);
        if (ao == null) {
            return;
        }
        workAction.saveAllEdited();
        workAction.sinkTitle(ao);
        workAction.saveAllEdited();
        workAction.loadTitleTree();
        ITitleLineVO vo = operationAction.getTitleByCaretLineContent();
        if (vo != null) {
            workAction.loadTitleNavigate(vo);
        }
    }

    /**
     * 安全删除include行
     */
    public void delete_include(KeyEvent event) {
        IncludeFilePathInfoAO ao = operationAction.deleteIncludeOperation(event);
        if (ao == null) {
            return;
        }
        workAction.deleteIncludeOnCaretLine(ao);
        workAction.saveAllEdited();
        workAction.loadTitleTree();
    }

    /**
     * 移动光标事件
     */
    public void move_caret() {
        ITitleLineVO vo = operationAction.getTitleByCaretLineContent();
        if (vo == null) {
            return;
        }
        workAction.loadTitleNavigate(vo);
    }

    /**
     * 打开输入弹窗
     */
    public void open_input_prompt(KeyEvent event) {
        IncludePromptAO ao = operationAction.getFilePromptByIncludeLine(event);
        if (ao != null) {
            workAction.openInputPrompt(ao);
            return;
        }
        if (operationAction.notCloseInputPrompt(event)) {
            return;
        }
        workAction.closeInputPrompt();
    }

    /**
     * 在提示弹窗打开时, 使用下键
     */
    public void select_next_on_prompt(ActionEvent event) {
        SelectPromptItemAO ao = operationAction.isSelectNextPromptOperation(event);
        if (ao != null) {
            workAction.selectPromptItem(ao);
            return;
        }
        workAction.defaultDownAction(event);
    }

    /**
     * 在提示弹窗打开时, 使用上键
     */
    public  void select_previous_on_prompt(ActionEvent event) {
        SelectPromptItemAO ao = operationAction.isSelectPreviousPromptOperation(event);
        if (ao != null) {
            workAction.selectPromptItem(ao);
            return;
        }
        workAction.defaultUpAction(event);
    }
}
