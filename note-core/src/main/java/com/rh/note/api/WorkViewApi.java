package com.rh.note.api;

import com.rh.note.ao.CaretPointAO;
import com.rh.note.ao.IncludeFilePathInfoAO;
import com.rh.note.ao.IncludePromptAO;
import com.rh.note.ao.InlineTitleAO;
import com.rh.note.ao.InputPromptItemAO;
import com.rh.note.ao.LineRangeAO;
import com.rh.note.ao.MatchIncludeInfoBySelectedTextAO;
import com.rh.note.ao.MatchTitleInfoBySelectedTextAO;
import com.rh.note.ao.RenameIncludeAO;
import com.rh.note.ao.SelectCaretLineAO;
import com.rh.note.ao.SelectPromptItemAO;
import com.rh.note.ao.TargetFilePathByIncludeJavaLineAO;
import com.rh.note.component.AdocTextPane;
import com.rh.note.component.InputPromptMenuItem;
import com.rh.note.component.TitleButton;
import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.constants.BaseConstants;
import com.rh.note.constants.PromptMessageEnum;
import com.rh.note.exception.UnknownLogicException;
import com.rh.note.file.JavaProConfig;
import com.rh.note.frame.WorkFrame;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.syntax.IncludeJavaSyntax;
import com.rh.note.sugar.IncludeJavaSyntaxSugar;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.sugar.IncludeSyntaxSugar;
import com.rh.note.syntax.TitleSyntax;
import com.rh.note.sugar.TitleSyntaxSugar;
import com.rh.note.util.ScrollPositionUtil;
import com.rh.note.view.ConfirmDialogView;
import com.rh.note.view.InputDialogView;
import com.rh.note.view.InputPromptItemView;
import com.rh.note.view.InputPromptMenuView;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.JOptionPane;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 工作窗口 操作
 */
@Component
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
        TextPaneView textPane = new TextPaneView().init(titleLine.getFilePath());
        if (textPane == null) {
            return;
        }
        TextScrollPaneView textScrollPane = new TextScrollPaneView().init(titleLine.getFilePath());
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
        TextPaneView textPane = new TextPaneView().init(filePath);
        return textPane.getTitleBeanPathByCaretLineContent();
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
        List<String> filePaths = TextPaneView.getAllEditedFilePath();
        if (CollectionUtils.isEmpty(filePaths)) {
            return;
        }
        filePaths.stream()
                .map(filePath -> new TextPaneView().init(filePath))
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
        TextPaneView textPane = new TextPaneView().init(filePath);
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
     * 用生成的include语句替换选择的内容
     */
    public MatchTitleInfoBySelectedTextAO getTitleInfoBySelectedText(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        // 获得选择内容
        TextPaneView textPane = new TextPaneView().init(filePath);
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
        TextPaneView textPane = new TextPaneView().init(filePath);
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

    /**
     * 获得include文件名, 在光标行
     */
    public String getIncludeFileNameOnCaretLine(AdocTextPane bean) {
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getCaretLineContent();
        IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
        if (syntax == null) {
            return null;
        }
        return syntax.getTargetFileName();
    }

    /**
     * 请求新名字
     */
    public @Nullable RenameIncludeAO requestNewName(AdocTextPane bean, String oldName) {
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getCaretLineContent();
        IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
        if (syntax == null) {
            return null;
        }
        // 请求新名字
        String newName = new InputDialogView().init(oldName, PromptMessageEnum.rename_include_message).getInputText();
        if (StringUtils.isBlank(newName)) {
            return null;
        }
        // 组装返回值
        return new RenameIncludeAO()
                .setFilePath(textPane.getFilePath())
                .setTargetFilePath(syntax.getTargetFilePath())
                .setNewName(newName);
    }

    /**
     * 选择include块文件名, 通过光标行
     */
    public String selectedIncludeFileNameOnCaretLine(AdocTextPane bean) {
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getCaretLineContent();
        IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
        if (syntax == null) {
            return null;
        }
        SelectCaretLineAO ao = new SelectCaretLineAO()
                .setStartOffset(syntax.getStartOffsetOfTargetFileName())
                .setLength(syntax.getLengthOfOfTargetFileName());
        textPane.selectCaretLine(ao);
        return syntax.getTargetFilePath();
    }

    /**
     * 选择根标题名
     * 和 {@link this#selectedIncludeFileNameOnCaretLine} 有关联, 没有选择成功时, 需要终止执行
     */
    public void selectRootTitleName(String filePath) throws UnknownLogicException {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        TextPaneView textPane = new TextPaneView().init(filePath);
        if (textPane == null) {
            throw new UnknownLogicException();
        }
        textPane.selectRootTitleName();
    }

    /**
     * 安全创建打开编辑区
     */
    public @Nullable TextPaneView safeCreateAndGetTextPane(AdocFileBeanPath beanPath) {
        if (beanPath == null) {
            return null;
        }

        String filePath = beanPath.getFilePath();
        TextPaneView textPaneOfExist = new TextPaneView().init(filePath);
        if (textPaneOfExist != null) {
            return textPaneOfExist;
        }

        TextPaneView.create(beanPath);
        return new TextPaneView().init(filePath);
    }

    /**
     * 添加并展示编辑区
     */
    public void addAndShowTextPane(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        TextScrollPaneView textScrollPane = new TextScrollPaneView().init(filePath);
        if (textScrollPane == null) {
            return;
        }

        // 添加编辑区
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        if (!tabbedPane.contains(textScrollPane)) {
            tabbedPane.add(textScrollPane);
        }
        // 展示编辑区
        tabbedPane.show(textScrollPane);
    }

    /**
     * 编辑区内容是否为空
     */
    public boolean isBlankTextPane(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return false;
        }

        TextPaneView textPane = new TextPaneView().init(filePath);
        if (textPane == null) {
            return false;
        }

        return textPane.isBlank();
    }

    /**
     * 向编辑区写入内容
     */
    public void writeTextPaneByFile(AdocFileBeanPath beanPath) {
        if (beanPath == null) {
            return;
        }

        TextPaneView textPane = new TextPaneView().init(beanPath.getFilePath());
        if (textPane == null) {
            return;
        }

        textPane.writeFileText(beanPath);
    }

    /**
     * 关闭旧的指向文件
     */
    public void closeTextPaneByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        TextScrollPaneView scrollPane = new TextScrollPaneView().init(filePath);
        // 从选项卡中删除
        TabbedPaneView tabbedPane = new TabbedPaneView().init();
        tabbedPane.remove(scrollPane);
        // 从swingBuilder中删除
        TextPaneView.deleteByFilePath(filePath);
        TextScrollPaneView.deleteByFilePath(filePath);
    }

    /**
     * 请求确认
     *
     * @return
     */
    public boolean requestConfirm(PromptMessageEnum message) {
        PromptMessageEnum msg = message != null ? message : PromptMessageEnum.are_you_sure_you_want_to_delete_safely;
        return new ConfirmDialogView().init(msg).isConfirm();
    }

    /**
     * 获得选择内容, 通过编辑区域文件地址
     */
    public @Nullable String getSelectContentByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        TextPaneView textPane = new TextPaneView().init(filePath);
        if (textPane == null) {
            return null;
        }

        return textPane.getSelectedText();
    }

    /**
     * 获得简单标题, 通过光标所在行
     */
    public @Nullable TitleLine getSimpleTitleLineByCaretLine(AdocTextPane bean) {
        if (bean == null) {
            return null;
        }

        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return null;
        }

        return textPane.getSimpleTitleByCaretLineContent();
    }

    /**
     * 选择行内容, 通过范围
     */
    public void selectLineByRange(LineRangeAO ao) {
        if (ao == null) {
            return;
        }

        TextPaneView textPane = new TextPaneView().init(ao.getFilePath());
        if (textPane == null) {
            return;
        }

        textPane.selectLineByRange(ao.getStartLineIndex(), ao.getEndLineIndex());
    }

    /**
     * 批量处理include语句中的文件路径
     */
    public @Nullable String batchHandleFilePathInIncludeSyntax(String selectedContent, String filePath, List<String> deleteFilePaths) {
        if (StringUtils.isBlank(selectedContent) || StringUtils.isBlank(filePath)) {
            return null;
        }

        return Arrays.stream(selectedContent.split("\n")).map(lineContent -> {
            if (StringUtils.isBlank(lineContent)) {
                return lineContent;
            }

            // 校验include语法是否正确
            IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
            if (syntax == null) {
                return lineContent;
            }

            if (!syntax.isChildPathOf(filePath)) {
                return lineContent;
            }

            String targetFilePath = syntax.getTargetFilePath();
            // 获得include指向文件内容 todo 这个对象的创建不应该放在view中
            AdocFileBeanPath beanPath = AdocFileBeanPath.create(targetFilePath);
            if (beanPath == null) {
                return lineContent;
            }

            TextPaneView textPane = this.safeCreateAndGetTextPane(beanPath);
            if (textPane == null) {
                return lineContent;
            }

            if (textPane.isBlank()) {
                this.writeTextPaneByFile(beanPath);
            }
            String fileContent = textPane.getText();
            if (StringUtils.isBlank(fileContent)) {
                return lineContent;
            }

            deleteFilePaths.add(targetFilePath);
            return fileContent;
        }).collect(Collectors.joining("\n"));
    }

    /**
     * 获得编辑区内容, 通过编辑区控件
     */
    public @Nullable String getFileContentByTextPane(TextPaneView textPane) {
        if (textPane == null) {
            return null;
        }

        return textPane.getText();
    }

    /**
     * 修改文本中所有的include语句的相对路径
     */
    public @Nullable String updateRelativePathOfIncludeSyntax(InlineTitleAO ao) {
        if (ao == null || ao.isErrorParams()) {
            return null;
        }

        return Arrays.stream(ao.getTargetFileContent().split("\n"))
                .map(lineContent -> { // 遍历每一行
                    // 校验是否为include语句, 父目录是否匹配
                    if (StringUtils.isBlank(lineContent)) {
                        return lineContent;
                    }

                    IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
                    if (syntax == null) {
                        return lineContent;
                    }

                    AdocFileTypeEnum fileType = AdocFileTypeEnum.matchByFilePath(ao.getFilePath());
                    if (fileType == null) {
                        return lineContent;
                    }

                    AdocFileTypeEnum targetFileType = fileType.getNextFileType();
                    if (targetFileType == null) {
                        return lineContent;
                    }

                    if (!targetFileType.isParentPathOf(syntax.getTargetFilePath())) {
                        return lineContent;
                    }

                    // include相对路径生成, 替换

                    String relativePathOfIncludeTargetFile = targetFileType.getRelativePathOfNextDirectory();
                    if (StringUtils.isBlank(relativePathOfIncludeTargetFile)) {
                        return lineContent;
                    }

                    String targetRelativePath = syntax.getTargetRelativePath();
                    int index = targetRelativePath.indexOf(relativePathOfIncludeTargetFile);
                    if (index < 0) {
                        return lineContent;
                    }

                    String newTargetRelativePath = targetRelativePath.replaceFirst(relativePathOfIncludeTargetFile, fileType.getRelativePathOfNextDirectory());
                    if (StringUtils.isBlank(newTargetRelativePath)) {
                        return lineContent;
                    }

                    syntax.setTargetRelativePath(newTargetRelativePath);
                    return syntax.toString();
                })
                .collect(Collectors.joining("\n"));
        // 合并为字符串
    }

    /**
     * 提示弹窗
     */
    public void promptPopup(PromptMessageEnum msg) {
        if (msg == null) {
            return;
        }

        JOptionPane.showMessageDialog(null,msg.getValue());
    }

    /**
     * 获得文件提示, 通过include行内容
     */
    public @Nullable IncludeJavaSyntaxSugar getPromptSyntaxByIncludeLine(AdocTextPane bean) {
        if (bean == null) {
            return null;
        }
        // 获取行内容
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getCaretLineContent();
        // 判断是否为include java语法, 待完善文件目录状态
        return new IncludeJavaSyntaxSugar().init(lineContent);
    }

    /**
     * 打开输入提示
     */
    public void openInputPrompt(IncludePromptAO ao) {
        if (ao == null) {
            return;
        }
        List<InputPromptItemAO> itemAOS = ao.getAoList();
        if (CollectionUtils.isEmpty(itemAOS)) {
            return;
        }
        List<InputPromptItemView> items = itemAOS.stream()
                .peek(InputPromptItemView::create)
                .map(itemAO -> new InputPromptItemView().init(itemAO.getCompleteValue()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        InputPromptMenuView inputPrompt = new InputPromptMenuView().init();
        inputPrompt.addAll(items);
        inputPrompt.show(ao);
        inputPrompt.selectFirst();
    }

    /**
     * 获得光标坐标
     */
    public @Nullable CaretPointAO getCaretPoint(AdocTextPane bean) {
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return null;
        }
        CaretPointAO ao = new CaretPointAO();
        ao.copy(textPane);
        return ao;
    }

    /**
     * 关闭输入提示
     */
    public void closeInputPrompt() {
        InputPromptMenuView inputPromptMenu = new InputPromptMenuView().init();
        inputPromptMenu.hidden();
        List<String> values = inputPromptMenu.getAllValues();
        inputPromptMenu.clearItems();
        if (CollectionUtils.isNotEmpty(values)) {
            values.forEach(InputPromptItemView::deleteByValue);
        }
    }

    /**
     * 编辑区获得焦点
     */
    public void textPaneRequestFocus(AdocTextPane bean) {
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return;
        }
        textPane.requestFocus();
    }

    /**
     * 提示菜单是否显示
     */
    public boolean isVisibleForPromptMenu() {
        InputPromptMenuView inputPromptMenu = new InputPromptMenuView().init();
        return inputPromptMenu.isVisible();
    }

    public void selectPromptItem(SelectPromptItemAO ao) {
        if (ao == null || ao.checkRequiredItems()) {
            return;
        }
        InputPromptMenuView promptMenu = new InputPromptMenuView().init();
        if (ao.isNext()) {
            promptMenu.next();
        }
        if (ao.isPrevious()) {
            promptMenu.previous();
        }
        promptMenu.showSelected();
    }

    /**
     * todo
     * 替换提示内容到编辑区
     */
    public void replacePromptItem(InputPromptMenuItem bean) {
        String proLabel = new JavaProConfig().getProLabel(bean.getResult());
//        InputPromptItemView inputPromptItem = InputPromptItemView.cast(bean);
        InputPromptItemView inputPromptItem = new InputPromptItemView().init(proLabel);
        if (inputPromptItem == null) {
            return;
        }
        TextScrollPaneView selectedTextPane = new TabbedPaneView().init().getSelectedTextPane();
        if (selectedTextPane == null) {
            return;
        }
        TextPaneView textPane = new TextPaneView().init(selectedTextPane.getFilePath());
        String lineContent = textPane.getCaretLineContent();
        IncludeJavaSyntaxSugar syntax = new IncludeJavaSyntaxSugar().init(lineContent);
        if (syntax == null || !syntax.isProPrompt()) {
            return;
        }
        if (StringUtils.isBlank(lineContent)) {
            return;
        }
        int index1 = lineContent.lastIndexOf('.');
        if (index1 < 0) {
            return;
        }
        textPane.replacePromptItem(index1, proLabel);
    }

    /**
     * todo
     * 替换包路径
     */
    public void replacePromptItemForPackage(InputPromptMenuItem bean) {
        if (bean == null || StringUtils.isBlank(bean.getText())) {
            System.out.println("0   " + bean.getText());
            return;
        }
        TextScrollPaneView selectedTextPane = new TabbedPaneView().init().getSelectedTextPane();
        if (selectedTextPane == null) {
            System.out.println("1");
            return;
        }
        TextPaneView textPane = new TextPaneView().init(selectedTextPane.getFilePath());
        String lineContent = textPane.getCaretLineContent();
        IncludeJavaSyntaxSugar syntax = new IncludeJavaSyntaxSugar().init(lineContent);
        if (syntax == null || !syntax.isPackagePrompt()) {
            System.out.println("2");
            return;
        }
        String regex = "^(\\s*=>j." + BaseConstants.pro_label_regex + "\\s+)(" + BaseConstants.package_path_regex + ")\\s*$";
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            System.out.println("3");
            return;
        }
        String str1 = matcher.group(1);
        String str2 = matcher.group(2);
        int index = getIndex(str2);
        textPane.replacePromptItem2(str1.length() + index, bean.getText());
    }

    /**
     * todo
     */
    private int getIndex(String str2) {
        if (StringUtils.isBlank(str2)) {
            return 0;
        }
        int index = str2.lastIndexOf('.');
        if (index < 0) {
            return 0;
        }
        return index + 1;
    }

    /**
     * 获得指向java文件路径, 通过光标行
     */
    public @Nullable TargetFilePathByIncludeJavaLineAO getTargetJavaFilePathByCaretLine(AdocTextPane bean) {
        TextPaneView textPane = TextPaneView.cast(bean);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getCaretLineContent();
        IncludeJavaSyntax syntax = new IncludeJavaSyntax().init(lineContent);
        if (syntax == null) {
            return null;
        }
        String targetAbsolutePath = syntax.getTargetAbsolutePath();
        if (StringUtils.isBlank(targetAbsolutePath)) {
            return null;
        }
        return new TargetFilePathByIncludeJavaLineAO()
                .setAbsolutePath(targetAbsolutePath)
                .setSourceFilePath(textPane.getFilePath())
                .setIncludeFilePath(syntax.getTargetRelativePath())
                .setMarkLineNumber1(syntax.getLineStart())
                .setMarkLineNumber2(syntax.getLineEnd());
    }
}
