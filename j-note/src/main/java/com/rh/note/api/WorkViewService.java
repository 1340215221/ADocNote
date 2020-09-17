package com.rh.note.api;

import com.rh.note.ao.SyntaxAnalysisAO;
import com.rh.note.base.BaseLine;
import com.rh.note.component.AdocTextPane;
import com.rh.note.component.TitleScrollPane;
import com.rh.note.file.AdocFile;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TextLine;
import com.rh.note.line.TitleLine;
import com.rh.note.util.ScrollPositionUtil;
import com.rh.note.view.RootTitleNodeView;
import com.rh.note.view.TabbedPaneView;
import com.rh.note.view.TextPaneView;
import com.rh.note.view.TextScrollPaneView;
import com.rh.note.view.TitleNavigateButtonView;
import com.rh.note.view.TitleNavigatePanelView;
import com.rh.note.view.TitleTreeModelView;
import com.rh.note.view.TitleTreeView;
import com.rh.note.view.WorkFrameView;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Comparator;
import java.util.List;

/**
 * 工作窗口服务
 */
public class WorkViewService {
    /**
     * 初始化窗口
     */
    public void initFrame() {
        WorkFrameView.create();
    }

    /**
     * 加载标题树的数据
     */
    public void loadTitleTreeData(TitleLine rootTitle) {
        RootTitleNodeView.create(rootTitle);
        RootTitleNodeView rootTitleNode = new RootTitleNodeView().init();
        TitleTreeModelView titleTreeModel = new TitleTreeModelView().init();
        titleTreeModel.setRoot(rootTitleNode);
        TitleTreeView titleTree = new TitleTreeView().init();
        titleTree.expandAllRow();
    }

    /**
     * 显示窗口
     */
    public void showFrame() {
        new WorkFrameView().init().show();
    }

    /**
     * 获得标题节点指向的文件
     */
    public TitleLine getAdocFileOfSelectTitleTreeNode() {
        TitleTreeView titleTree = new TitleTreeView().init();
        return titleTree.getTitleOfSelectedNode();
    }

    /**
     * 创建不存在的编辑区
     */
    public TextPaneView createNonExistentTextPane(AdocFile adocFile) {
        if (adocFile == null) {
            return null;
        }
        TextPaneView textPane = new TextPaneView().initByFilePath(adocFile.getFilePath());
        if (textPane != null) {
            return textPane;
        }

        TextPaneView.create(adocFile);
        TextScrollPaneView textScrollPane = new TextScrollPaneView().initByFilePath(adocFile.getFilePath());
        if (textScrollPane == null) {
            return null;
        }
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        tabbedPane.add(textScrollPane, adocFile.getFileName());
        return new TextPaneView().initByFilePath(adocFile.getFilePath());
    }

    /**
     * 显示指定编辑区
     */
    public void showTextPane(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }

        TextScrollPaneView textScrollPane = new TextScrollPaneView().initByFilePath(filePath);
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        tabbedPane.show(textScrollPane);
    }

    /**
     * 加载空白编辑区数据
     */
    public void loadTextPaneData(TextPaneView textPane, File file) {
        if (textPane == null || file == null || !file.exists() || !file.isFile()) {
            return;
        }

        textPane.read(file);
    }

    /**
     * 加载导航栏,通过标题
     */
    public void loadTitleNavigateByTitle(TitleLine titleLine) {
        if (titleLine == null) {
            return;
        }
        List<TitleLine> parentTitles = titleLine.getParentTitles();
        if (CollectionUtils.isEmpty(parentTitles)) {
            return;
        }
        TitleNavigatePanelView titleNavigatePanel = new TitleNavigatePanelView().init();
        titleNavigatePanel.clearTitle();
        parentTitles.stream().sorted(Comparator.comparing(title -> title.getTitleSyntax().getLevel())).forEach(title -> {
            TitleNavigateButtonView.create(title);
            TitleNavigateButtonView titleNavigateButton = new TitleNavigateButtonView().initByTitleName(title.getTitleName());
            titleNavigatePanel.add(titleNavigateButton);
        });
    }

    public TitleLine getCursorTitleOfSelectedTab() {
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        String filePath = tabbedPane.getFilePath();
        TextPaneView textPane = new TextPaneView().initByFilePath(filePath);
        if (textPane == null) {
            return null;
        }
        return textPane.getCursorTitle();
    }

    /**
     * 从title中获得adocfile
     */
    public AdocFile getAdocFileOfTitle(TitleLine titleLine) {
        if (titleLine == null) {
            return null;
        }
        return titleLine.getAdocFile();
    }

    /**
     * 获得include对象, 通过选择面板的光标所在行
     */
    public IncludeLine getIncludeOfCursorLineOfSelectedPanel() {
        String filePath = new TabbedPaneView().init().getFilePath();
        TextPaneView textPane = new TextPaneView().initByFilePath(filePath);
        if (textPane == null) {
            return null;
        }
        BaseLine baseLine = textPane.getCursorLine();
        return baseLine instanceof IncludeLine ? ((IncludeLine) baseLine) : null;
    }

    /**
     * 定位到标题行
     */
    public void positioningToTitleRow(TitleLine titleLine) {
        if (titleLine == null) {
            return;
        }
        TextPaneView textPane = new TextPaneView().initByFilePath(titleLine.getFilePath());
        if (textPane == null) {
            return;
        }
        TextScrollPaneView textScrollPane = new TextScrollPaneView().initByFilePath(titleLine.getFilePath());
        ScrollPositionUtil.builder()
                .adocTextPane(textPane.getBean())
                .titleScrollPane((TitleScrollPane) textScrollPane.getBean())
                .lineNumber(titleLine.getLineNumber())
                .build()
                .positioningToTitleRow();
    }

    /**
     * 获得行对象, 通过选中编辑区光标所在行
     */
    public SyntaxAnalysisAO getLineBeanOfCursorLineOfSelectedPanel() {
        String filePath = new TabbedPaneView().init().getFilePath();
        BaseLine baseLine = new TextPaneView().initByFilePath(filePath).getCursorLine();
        return SyntaxAnalysisAO.create(baseLine instanceof TextLine ? ((TextLine) baseLine) : null);
    }

    /**
     * 生成引用块
     */
    public void generateIncludeBlock(SyntaxAnalysisAO ao) {
        if (ao == null || ao.getTextLine() == null) {
            return;
        }
        IncludeLine includeLine = ao.generateIncludeLine();
        TextPaneView textPane = new TextPaneView().initByFilePath(ao.getTextLine().getAdocFile().getFilePath());
        if (textPane == null) {
            return;
        }
        //todo
    }
}
