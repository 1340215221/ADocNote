package com.rh.note.api;

import com.rh.note.component.TitleButton;
import com.rh.note.file.AdocFile;
import com.rh.note.frame.WorkFrame;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.TitleBeanPath;
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
import com.rh.note.vo.ITitleLineVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;

/**
 * 工作窗口 操作
 */
public class WorkViewApi {
    /**
     * 显示窗口
     */
    public void showFrame() {
        new WorkFrameView().init().show();
    }

    /**
     * 初始化窗口
     */
    public void initFrame() {
        new WorkFrame().start();
    }

    /**
     * 加载标题树数据
     */
    public void loadTitleTree(TitleLine rootTitle) {
        if (rootTitle == null) {
            return;
        }
        // 加载节点数据
        RootTitleNodeView.create(rootTitle);
        RootTitleNodeView rootTitleNode = new RootTitleNodeView().init();
        TitleTreeModelView titleTreeModel = new TitleTreeModelView().init();
        titleTreeModel.setRoot(rootTitleNode);
        // 展开所有节点
        new TitleTreeView().init().expandRowAllNode();
    }

    /**
     * 获得选择的标题节点
     */
    public ITitleLineVO getSelectedTitleNode() {
        return new TitleTreeView().init().getSelectedTitleNode();
    }

    /**
     * 展示已打开的编辑区
     */
    public @Nullable TextPaneView showExistTextPane(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        TextPaneView textPane = new TextPaneView().initByFilePath(filePath);
        if (textPane == null) {
            return null;
        }
        TextScrollPaneView textScrollPane = new TextScrollPaneView().initByFilePath(filePath);
        if (textScrollPane == null) {
            return null;
        }
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        tabbedPane.show(textScrollPane);
        return textPane;
    }

    /**
     * 创建编辑区, 通过文件对象
     */
    public void createTextPaneByFile(AdocFileBeanPath beanPath) {
        if (beanPath == null) {
            return;
        }
        // 创建编辑区
        TextPaneView.create(beanPath);
        // 添加编辑区
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        TextScrollPaneView textScrollPane = new TextScrollPaneView().initByFilePath(beanPath.getFilePath());
        tabbedPane.add(textScrollPane);
        // 设置文件内容
        TextPaneView textPane = new TextPaneView().initByFilePath(beanPath.getFilePath());
        textPane.setText(beanPath);
        // 显示添加的编辑区
        tabbedPane.show(textScrollPane);
    }

    /**
     * 加载标题导航
     */
    public void loadTitleNavigate(TitleLine titleLine) {
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
            TitleNavigateButtonView.create(title.getBeanPath());
            TitleNavigateButtonView titleNavigateButton = new TitleNavigateButtonView().initByBeanPath(title.getBeanPathStr());
            titleNavigatePanel.add(titleNavigateButton);
        });
    }

    /**
     * 获得被选择的编辑区
     */
    public ITitleLineVO getSelectedTextPane() {
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        TextScrollPaneView textScrollPane = tabbedPane.getSelectedTextPane();
        if (textScrollPane == null) {
            return null;
        }
        String filePath = textScrollPane.getFilePath();
        RootTitleNodeView rootTitleNode = new RootTitleNodeView().init();
        return rootTitleNode.getTitleByFilePath(filePath);
    }

    /**
     * 获得按钮对应的标题
     */
    public @Nullable TitleLine getFileRootTitleByButton(TitleButton source) {
        if (source == null) {
            return null;
        }
        TitleNavigateButtonView titleNavigateButton = TitleNavigateButtonView.cast(source);
        if (titleNavigateButton == null) {
            return null;
        }
        TitleBeanPath beanPath = titleNavigateButton.getBeanPath();
        RootTitleNodeView rootTitleNode = new RootTitleNodeView().init();
        return rootTitleNode.getTitleByBeanPath(beanPath);
    }

    /**
     * 判断是否为根标题
     */
    public boolean checkIsFileRootTitle(TitleLine titleLine) {
        if (titleLine == null) {
            return false;
        }
        return titleLine.checkIsFileRootTitle();
    }

    /**
     * 定位到行, 通过标题
     */
    public void positioningLineByTitle(TitleLine titleLine) {
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
                .adocScrollPane(textScrollPane.getBean())
                .lineNumber(titleLine.getLineNumber())
                .build()
                .positioningToTitleRow();
    }

    /**
     * 获得标题, 通过光标所在行内容
     */
    public @Nullable TitleBeanPath getSimpleTitleByCaretLineContent() {
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        TextScrollPaneView textScrollPane = tabbedPane.getSelectedTextPane();
        if (textScrollPane == null) {
            return null;
        }
        String filePath = textScrollPane.getFilePath();
        TextPaneView textPane = new TextPaneView().initByFilePath(filePath);
        return textPane.getTitleByCaretLineContent();
    }

    /**
     * 获得标题, 通过简单标题
     */
    public @Nullable TitleLine getTitleByBeanPath(TitleBeanPath beanPath) {
        if (beanPath == null) {
            return null;
        }
        RootTitleNodeView rootTitleNode = new RootTitleNodeView().init();
        return rootTitleNode.getTitleByBeanPath(beanPath);
    }
}