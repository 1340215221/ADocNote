package com.rh.note.api;

import cn.hutool.core.io.FileUtil;
import com.rh.note.ao.*;
import com.rh.note.bean.SyntaxStyleContext;
import com.rh.note.common.IShowProgress;
import com.rh.note.config.SyntaxHighlightConfig;
import com.rh.note.constants.PromptMessageEnum;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.line.TitleLine;
import com.rh.note.path.FileBeanPath;
import com.rh.note.sugar.AdocIncludeSyntaxSugar;
import com.rh.note.sugar.TitleSyntaxSugar;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.TitleSyntax;
import com.rh.note.view.*;
import com.rh.note.vo.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * 工作窗口操作 接口
 */
@Component
public class WorkViewApi {

    @Autowired
    private SyntaxHighlightConfig syntaxHighlightConfig;

    /**
     * 更新根标题
     */
    public void updateRootNode(TitleLine rooTitle) {
        if (rooTitle == null) {
            return;
        }
        // 删除旧节点
        RootTitleNodeView oldNodeView = new RootTitleNodeView().init();
        if (oldNodeView != null) {
            oldNodeView.deleteAllNote();
        }
        // 更新新节点
        RootTitleNodeView nodeView = new RootTitleNodeView().create(rooTitle);
        TreeModelView modelView = new TreeModelView().init();
        modelView.setRootNode(nodeView);
        new TitleTreeView().init().expandAllNode();
    }

    /**
     * 显示已打开文件
     */
    public void showOpenedFileByFilePath(String filePath) {
        TextScrollPaneView scrollPaneView = new TextScrollPaneView().init(filePath);
        if (scrollPaneView == null) {
            return;
        }
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        tabbedPaneView.show(scrollPaneView);
    }

    /**
     * 判断文件在编辑区已打开
     */
    public boolean checkIsOpenedFile(String filePath) {
        return new TextScrollPaneView().init(filePath) != null;
    }

    /**
     * 保存编辑区内容,通过文件路径
     */
    public void saveTextPaneFileByFilePaths(TextPaneFileWritersAO ao) {
        if (ao == null || ao.checkMissRequiredParams()) {
            return;
        }
        try {
            ao.forEach((String filePath, Writer writer) -> {
                AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
                if (textPaneView == null) {
                    return;
                }
                textPaneView.writerToFile(writer);
            });
        }finally {
            ao.closeAllWriter();
        }
    }

    /**
     * 生成adoc编辑区
     */
    public void createAdocTextPane(FileBeanPath beanPath, Reader reader) {
        if (beanPath == null || reader == null) {
            return;
        }
        // 创建编辑区
        AdocTextPaneView textPaneView = new AdocTextPaneView().create(beanPath);
        // 加载编辑区内容
        textPaneView.initContent(reader);
    }

    /**
     * 生成java编辑区
     */
    public void createJavaTextPane(FileBeanPath beanPath, Reader reader) {
        if (beanPath == null || reader == null) {
            return;
        }
        // 创建编辑区
        ReadOnlyTextPaneView textPaneView = new ReadOnlyTextPaneView().create(beanPath);
        // 加载编辑区内容
        textPaneView.initContent(reader);
    }

    /**
     * 获得被选择节点的标题对象
     */
    public @NotNull FindTitleNodeSelectedVO getTitleLineBySelectedNode() {
        TitleTreeView treeView = new TitleTreeView().init();
        TitleLine titleLine = treeView.getTitleLineBySelectedNode();
        FindTitleNodeSelectedVO vo = new FindTitleNodeSelectedVO();
        vo.copy(titleLine);
        return vo;
    }

    /**
     * 获得被选择的编辑区对应的文件地址
     */
    public @Nullable String getFilePathOfTextPaneSelected() {
        return new TabbedPaneView().init().getFilePathOfTextPaneSelected();
    }

    /**
     * 获得选项卡中的编辑控件对应的文件地址
     */
    public @Nullable List<String> getFilePathsOfTextPaneByTabbedPane() {
        return new TabbedPaneView().init().getFilePathsOfTextPane();
    }

    /**
     * 关闭编辑区
     */
    public void closeTextPaneByFilePaths(List<String> filePaths) {
        if (CollectionUtils.isEmpty(filePaths)) {
            return;
        }
        filePaths.forEach(filePath -> {
            AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
            if (textPaneView != null) {
                textPaneView.close();
                return;
            }
            ReadOnlyTextPaneView readOnlyTextPaneView = new ReadOnlyTextPaneView().init(filePath);
            if (readOnlyTextPaneView != null) {
                readOnlyTextPaneView.close();
            }
        });
    }

    /**
     * 获得include行指向的adoc文件路径, 通过被选择的编辑区
     */
    public @Nullable FindIncludePathInSelectedTextPaneVO getIncludePathInSelectedTextPane() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        String filePath = tabbedPaneView.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return null;
        }
        String lineContent = textPaneView.getCaretLineContent();
        IncludeSyntax includeSyntax = new IncludeSyntax().init(lineContent);
        if (includeSyntax == null) {
            return null;
        }
        FindIncludePathInSelectedTextPaneVO vo = new FindIncludePathInSelectedTextPaneVO();
        vo.copy(includeSyntax);
        vo.setCurrentFilePath(filePath);
        return vo;
    }

    /**
     * 生成标题语法, 通过被选择的编辑区光标行内容
     */
    public void generateTitleSyntaxByCaretLineOfSelectedTextPane() {
        // 获取光标行内容
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        String filePath = tabbedPaneView.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        String lineContent = textPaneView.getCaretLineContent();
        // 判断行内容是否为标题语法
        TitleSyntaxSugar syntaxSugar = new TitleSyntaxSugar().init(lineContent);
        if (syntaxSugar == null) {
            return;
        }
        // 选择当前行
        textPaneView.selectCaretLine();
        // 生成标题语句
        TitleSyntax syntax = new TitleSyntax().init(syntaxSugar);
        String newContent = syntax.toString();
        // 替换选择内容
        textPaneView.replaceSelectedContent(newContent);
    }

    /**
     * 获得没有被选择的编辑区
     */
    public @NotNull List<String> getFilePathsOfTextPaneNotSelected() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        return tabbedPaneView != null ? tabbedPaneView.getFilePathOfTextPaneNotSelected() : Collections.emptyList();
    }

    /**
     * 判断是快捷语法行, 通过被选择编辑区的光标行内容
     */
    public boolean checkIsSyntaxSugarByCaretLineOfSelectedTextPane() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        String filePath = tabbedPaneView.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return false;
        }
        String lineContent = textPaneView.getCaretLineContent();
        return new TitleSyntaxSugar().init(lineContent) != null || new AdocIncludeSyntaxSugar().init(lineContent) != null;
    }

    /**
     * 刷新被选择编辑区语法高亮
     */
    public void refreshSyntaxHighlightOfAdocTextPaneByFilePath(String filePath) {
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        SyntaxStyleContext styleContext = SyntaxStyleContext.getInstance(filePath, syntaxHighlightConfig.getDefaultStyle());
        textPaneView.forEachLine(styleContext);
    }

    /**
     * 刷新被选择编辑区语法高亮
     */
    public void refreshSyntaxHighlightOfJavaTextPaneByFilePath(String filePath) {
        ReadOnlyTextPaneView textPaneView = new ReadOnlyTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        SyntaxStyleContext styleContext = SyntaxStyleContext.getInstance(filePath, syntaxHighlightConfig.getDefaultStyle());
        textPaneView.forEachLine(styleContext);
    }

    /**
     * 刷新被选择编辑区语法高亮
     */
    public void refreshSyntaxHighlightOfAdocTextPaneSelected() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        String filePath = tabbedPaneView.getFilePathOfTextPaneSelected();
        refreshSyntaxHighlightOfAdocTextPaneByFilePath(filePath);
    }

    /**
     * 生成include语句, 通过被选择的编辑区的光标行内容
     */
    public @Nullable GenerateIncludeSyntaxVO generateIncludeSyntaxByCaretLineOfSelectedTextPane() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        String filePath = tabbedPaneView.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return null;
        }
        String lineContent = textPaneView.getCaretLineContent();
        AdocIncludeSyntaxSugar syntaxSugar = new AdocIncludeSyntaxSugar().init(lineContent);
        if (syntaxSugar == null) {
            return null;
        }
        textPaneView.selectCaretLine();
        IncludeSyntax syntax = new IncludeSyntax().init(syntaxSugar, filePath);
        if (syntax == null) {
            return null;
        }
        String newContent = syntax.toString();
        textPaneView.replaceSelectedContent(newContent);
        return new GenerateIncludeSyntaxVO(syntax, filePath, syntaxSugar.getTargetLevel());
    }

    /**
     * 请求光标行include文件新名字
     */
    public @Nullable RequestNewNameOfIncludeOnCaretLineVO requestNewNameOfIncludeOnCaretLine(String filePath) {
        // 获得include指向文件的名字
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return null;
        }
        String lineContent = textPaneView.getCaretLineContent();
        IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
        if (syntax == null) {
            return null;
        }
        // 请求新名字
        String fileName = FileUtil.mainName(syntax.getIncludePath());
        InputDialogView dialogView = new InputDialogView().init(fileName, PromptMessageEnum.rename_include_message);
        String newName = dialogView.getInputText();
        return RequestNewNameOfIncludeOnCaretLineVO.getInstance(filePath, syntax, newName);
    }

    /**
     * 更新adoc编辑区根标题名字
     */
    public void updateRootTitleOfTextPane(UpdateRootTitleOfTextPaneAO ao) {
        if (ao == null || ao.checkMissRequiredParams()) {
            return;
        }
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(ao.getFilePath());
        if (textPaneView == null) {
            return;
        }
        textPaneView.updateRootTitleName(ao.getNewTitleName());
    }

    /**
     * 更新include行内容
     */
    public void updateCaretLineContent(UpdateCaretLineAO ao) {
        if (ao == null || ao.checkMissRequiredParams()) {
            return;
        }
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(ao.getFilePath());
        if (textPaneView == null) {
            return;
        }
        textPaneView.updateCaretLineContent(ao.getNewLineContent());
    }

    /**
     * 初始化adoc编辑区的内容
     */
    public void initOpenedAdocTextPaneContent(InitAdocTextPaneContentAO ao) {
        if (ao == null || ao.checkMissRequiredParams()) {
            return;
        }
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(ao.getFilePath());
        if (textPaneView == null) {
            return;
        }
        textPaneView.initContent(ao.getInitContent());
    }

    /**
     * 确认删除include语句, 通过被选择的编辑区的光标行内容
     */
    public @Nullable ConfirmDeleteIncludeVO confirmDeleteIncludeOnCaretLineOfTextPaneSelected() {
        // 判断是否为include语法
        String filePath = this.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return null;
        }
        String lineContent = textPaneView.getCaretLineContent();
        IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
        if (syntax == null) {
            return null;
        }
        // 弹窗确认
        ConfirmDialogView confirmDialogView = new ConfirmDialogView().init(PromptMessageEnum.make_sure_to_delete_the_include_statement);
        if (!confirmDialogView.isConfirm()) {
            return null;
        }
        // 返回值
        ConfirmDeleteIncludeVO vo = new ConfirmDeleteIncludeVO();
        vo.copy(syntax);
        vo.setFilePath(filePath);
        return vo;
    }

    /**
     * 删除光标行内容
     */
    public void deleteCaretLine(String filePath) {
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        textPaneView.deleteCaretLine();
    }

    /**
     * 弹窗提示git操作失败
     */
    public boolean promptGitOperationFailed() {
        ConfirmDialogView dialogView = new ConfirmDialogView().init(PromptMessageEnum.GIT_OPERATION_FAILED);
        return dialogView.isConfirm();
    }

    /**
     * 打开一个进度弹窗
     */
    public @Nullable IShowProgress openProgressDialog() {
        ProgressLabelView labelView = new ProgressLabelView().init();
        labelView.reset();
        ProgressBarView barView = new ProgressBarView().init();
        barView.reset();
        IShowProgress callback = barView.getCallback(labelView);
        new ProgressDialogView().init().show();
        return callback;
    }

    /**
     * 关闭进度弹窗
     */
    public void closeProgressDialog() {
        new ProgressDialogView().init().close();
    }

    /**
     * 默认浏览器打开网址
     */
    public void openUrlOfSelectedTextPane() {
        // 获得光标行内容
        String filePath = this.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        String url = textPaneView.getUrlByCaret();
        if (StringUtils.isBlank(url)) {
            return;
        }
        // 用默认浏览器打开url
        if(!Desktop.isDesktopSupported()){
            return;
        }
        Desktop desktop= Desktop.getDesktop();
        if (!desktop.isSupported(Desktop.Action.BROWSE)) {
            return;
        }
        URI uri= URI.create(url);
        try {
            desktop.browse(uri);
        } catch (IOException e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_OPEN_URL_WITH_BROWSER);
        }
    }

    /**
     * 标记待完成
     */
    public void markTodo(MarkTodoAO ao) {
        String filePath = this.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        String lineContent = textPaneView.getCaretLineContent();
        SelectAndReplaceAO srAO = ao.parsing(lineContent);
        if (srAO == null) {
            return;
        }
        textPaneView.selectAndReplace(srAO);
    }

    /**
     * 获得被选择节点的级别
     */
    public @Nullable Integer getTitleLevelOfSelectedNode() {
        TitleTreeView treeView = new TitleTreeView().init();
        TitleLine titleLine = treeView.getTitleLineBySelectedNode();
        return titleLine != null ? titleLine.getLevel() : null;
    }

    /**
     * 展开指定级别的节点
     */
    public void expandNodeByLevel(Integer level) {
        if (level == null || level < 1) {
            return;
        }
        TitleTreeView treeView = new TitleTreeView().init();
        treeView.expandNodeByLevel(level);
    }
}
