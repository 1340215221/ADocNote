package com.rh.note.action;

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

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

/**
 * 工作窗口--入口
 */
public interface IWorkAction {
    /**
     * 提交adoc内容
     */
    void commit();

    /**
     * git push
     */
    void push();

    /**
     * 打开编辑区, 通过标题节点
     */
    void openTextPaneByTitleNode(ITitleLineVO vo);

    /**
     * 加载标题树
     */
    void loadTitleTree();

    /**
     * 加载标题导航
     */
    void loadTitleNavigate(ITitleLineVO vo);

    /**
     * 定位行, 通过标题
     */
    void positioningLineByTitle(ITitleLineVO vo);

    /**
     * 保存所有编辑区内容
     */
    void saveAllEdited();

    /**
     * 默认回车操作
     */
    void defaultEnterOperation(ActionEvent event);

    /**
     * 生成include块
     */
    void generateIncludeSyntaxBySelectedText(GenerateIncludeSyntaxAO ao);

    /**
     * 生成title块
     */
    void generateTitleSyntaxBySelectedText(GenerateTitleSyntaxAO ao);

    /**
     * 删除include语法行和指向文件
     */
    void deleteIncludeOnCaretLine(IncludeFilePathInfoAO ao);

    /**
     * 重命名include块
     */
    void renameInclude(RenameIncludeAO ao);

    /**
     * 下沉标题
     */
    void sinkTitle(ITitleContentAO ao);

    /**
     * 内联标题
     */
    void inlineTitle(InlineTitleAO ao);

    /**
     * 打开输入提示
     */
    void openInputPrompt(IncludePromptAO ao);

    /**
     * 关闭输入提示
     */
    void closeInputPrompt();

    /**
     * 选择提示
     */
    void selectPromptItem(SelectPromptItemAO ao);

    /**
     * 默认下键操作
     */
    void defaultDownAction(ActionEvent event);

    /**
     * 默认上键操作
     */
    void defaultUpAction(ActionEvent event);

    /**
     * 替换提示内容到编辑区
     */
    void replacePromptItem(MouseEvent mouseEvent);

    /**
     * Java include快捷语法转换
     * @param ao
     */
    void generateJavaIncludeSyntaxBySelectedText(GenerateJavaIncludeSyntaxAO ao);

    /**
     * 打开java文件
     */
    void openJavaFile(TargetFilePathByIncludeJavaLineAO ao);
}
