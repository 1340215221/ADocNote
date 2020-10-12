package com.rh.note.api;

import com.rh.note.ao.IncludeFilePathInfoAO;
import com.rh.note.ao.MatchIncludeInfoBySelectedTextAO;
import com.rh.note.ao.MatchTitleInfoBySelectedTextAO;
import com.rh.note.component.AdocTextPane;
import com.rh.note.component.TitleButton;
import com.rh.note.frame.WorkFrame;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.IncludeSyntaxSugar;
import com.rh.note.syntax.TitleSyntax;
import com.rh.note.syntax.TitleSyntaxSugar;
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
import com.rh.note.vo.WriterVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

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

    /**
     * 全部编辑区内容写入到文件
     */
    public void writeAllEdited(Function<String, WriterVO> getFileWriterFunction) {
        if (getFileWriterFunction == null) {
            return;
        }
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        List<String> filePaths = tabbedPane.getAllFilePathOfExistFile();
        if (CollectionUtils.isEmpty(filePaths)) {
            return;
        }
        filePaths.stream()
                .map(filePath -> new TextPaneView().initByFilePath(filePath))
                .filter(Objects::nonNull)
                .forEach(textPane -> textPane.write(getFileWriterFunction));
    }

    /**
     * 是否为include, 在光标所在行
     */
    public boolean checkIsIncludeSyntaxSugarOnCaretLine(AdocTextPane bean) {
        if (bean == null) {
            return false;
        }
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return false;
        }
        String lineContent = textPane.getCaretLineContent();
        if (StringUtils.isBlank(lineContent)) {
            return false;
        }
        IncludeSyntaxSugar includeSyntaxSugar = new IncludeSyntaxSugar().init(lineContent);
        return includeSyntaxSugar != null;
    }

    /**
     * 是否为include, 在光标所在行
     */
    public boolean checkIsTitleSyntaxSugarOnCaretLine(AdocTextPane bean) {
        if (bean == null) {
            return false;
        }
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return false;
        }
        String lineContent = textPane.getCaretLineContent();
        if (StringUtils.isBlank(lineContent)) {
            return false;
        }
        TitleSyntaxSugar syntaxSugar = new TitleSyntaxSugar().init(lineContent);
        return syntaxSugar != null;
    }

    /**
     * 选择光标所在行
     */
    public void selectCaretLine(AdocTextPane bean) {
        if (bean == null) {
            return;
        }
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return;
        }
        textPane.selectCaretLine();
    }

    /**
     * 用生成的include语句替换选择的内容
     */
    public @Nullable MatchIncludeInfoBySelectedTextAO getIncludeInfoBySelectedText(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        // 获得选择内容
        TextPaneView textPane = new TextPaneView().initByFilePath(filePath);
        if (textPane == null) {
            return null;
        }
        String selectedText = textPane.getSelectedText();
        // include快捷语法转化为include语法块
        IncludeSyntaxSugar includeSyntaxSugar = new IncludeSyntaxSugar().init(selectedText);
        if (includeSyntaxSugar == null) {
            return null;
        }
        IncludeSyntax includeSyntax = includeSyntaxSugar.copyToByFilePath(filePath);
        String includeSyntaxText = includeSyntax.toString();
        // 返回值
        return new MatchIncludeInfoBySelectedTextAO()
                .setFilePath(includeSyntax.getTargetFilePath())
                .setIncludeSyntaxSugar(includeSyntaxSugar)
                .setIncludeText(includeSyntaxText);
    }

    /**
     *用生成的include语句替换选择的内容
     */
    public MatchTitleInfoBySelectedTextAO getTitleInfoBySelectedText(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        // 获得选择内容
        TextPaneView textPane = new TextPaneView().initByFilePath(filePath);
        if (textPane == null) {
            return null;
        }
        String selectedText = textPane.getSelectedText();
        // include快捷语法转化为include语法块
        TitleSyntaxSugar syntaxSugar = new TitleSyntaxSugar().init(selectedText);
        if (syntaxSugar == null) {
            return null;
        }
        TitleSyntax titleSyntax = syntaxSugar.copyTo();
        String titleSyntaxText = titleSyntax.toString();
        // 返回值
        return new MatchTitleInfoBySelectedTextAO().setTitleText(titleSyntaxText);
    }

    /**
     * 用生成的include语句替换选择的内容
     */
    public void replaceSelectedText(String filePath, String text) {
        if (StringUtils.isBlank(filePath) || text == null) {
            return;
        }
        // 获得选择内容
        TextPaneView textPane = new TextPaneView().initByFilePath(filePath);
        if (textPane == null) {
            return;
        }
        textPane.replaceSelectedText(text);
    }

    /**
     * 获得根标题, 通过光标所在行的include行指向的文件
     */
    public @Nullable TitleLine getRootTitleOfCaretLineIncludeTargetFile(AdocTextPane source) {
        TextPaneView textPane = TextPaneView.cast(source);
        if (textPane == null) {
            return null;
        }
        // 获得光标所在行的include对象
        String lineContent = textPane.getCaretLineContent();
        IncludeSyntax includeSyntax = new IncludeSyntax().init(lineContent);
        if (includeSyntax == null) {
            return null;
        }
        // 获得指向文件的根标题
        TitleBeanPath beanPath = includeSyntax.getBeanPathOfTargetFileRootTitle();
        RootTitleNodeView rootTitleNode = new RootTitleNodeView().init();
        return rootTitleNode.getTitleByBeanPath(beanPath);
    }

    /**
     * 获得include文件信息, 通过光标所在行
     */
    public @Nullable IncludeFilePathInfoAO getIncludeFilePathInfoOnCaretLine(AdocTextPane bean) {
        if (bean == null) {
            return null;
        }
        // 获得光标所在行的include对象
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getCaretLineContent();
        IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
        if (syntax == null) {
            return null;
        }
        // 组装返回值
        return new IncludeFilePathInfoAO()
                .setFilePath(textPane.getFilePath())
                .setTargetFilePath(syntax.getTargetFilePath());
    }
}
