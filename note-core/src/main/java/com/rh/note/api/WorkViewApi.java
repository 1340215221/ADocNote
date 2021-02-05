package com.rh.note.api;

import com.rh.note.ao.InitAdocTextPaneContentAO;
import com.rh.note.ao.TextPaneFileWritersAO;
import com.rh.note.config.SyntaxHighlightConfig;
import com.rh.note.exception.UnknownBusinessSituationException;
import com.rh.note.line.TitleLine;
import com.rh.note.path.FileBeanPath;
import com.rh.note.sugar.AdocIncludeSyntaxSugar;
import com.rh.note.sugar.TitleSyntaxSugar;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.TitleSyntax;
import com.rh.note.view.*;
import com.rh.note.vo.FindIncludePathInSelectedTextPaneVO;
import com.rh.note.vo.FindTitleNodeSelectedVO;
import com.rh.note.vo.GenerateIncludeSyntaxVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.io.Writer;
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
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        TextScrollPaneView scrollPaneView = new TextScrollPaneView().init(filePath);
        if (scrollPaneView == null) {
            throw new UnknownBusinessSituationException();
        }
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        tabbedPaneView.show(scrollPaneView);
    }

    /**
     * 判断adoc文件已打开
     */
    public boolean checkIsOpenedFile(String filePath) {
        return StringUtils.isNotBlank(filePath) && new AdocTextPaneView().init(filePath) != null;
    }

    /**
     * 保存编辑区内容,通过文件路径
     */
    public void saveTextPaneFileByFilePaths(TextPaneFileWritersAO ao) {
        if (ao == null || ao.checkMissRequiredParams()) {
            return;
        }
        ao.forEach((String filePath, Writer writer) -> {
            AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
            if (textPaneView == null) {
                return;
            }
            textPaneView.writerToFile(writer);
        });
    }

    /**
     * 生成编辑区
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
            if (textPaneView == null) {
                return;
            }
            textPaneView.close();
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
    public void refreshSyntaxHighlightOfTextPaneSelected() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        String filePath = tabbedPaneView.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        // 可以考虑两个方法一起执行, 一行一行刷新语法样式
        textPaneView.clearAllFontStyle(syntaxHighlightConfig);
        textPaneView.refreshSyntaxHighlight(syntaxHighlightConfig);
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
     * 光标行字体恢复为默认风格
     */
    public void clearLinefeedOfFontStyleBeforeCaretLine() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        String filePath = tabbedPaneView.getFilePathOfTextPaneSelected();
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        textPaneView.clearLinefeedOfFontStyleBeforeCaretLine(syntaxHighlightConfig);
    }
}
