package com.rh.note.api;

import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
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
    public AdocFile getAdocFileOfSelectTitleTreeNode() {
        TitleTreeView titleTree = new TitleTreeView().init();
        return titleTree.getAdocFileOfSelectedNode();
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

    public TitleLine getRootTitleOfSelectedTab() {
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        return tabbedPane.getRootTitleOfSelectedTab();
    }
}
