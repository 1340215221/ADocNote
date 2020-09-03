package com.rh.note.api;

import com.rh.note.common.IAdocFile;
import com.rh.note.common.IForEach;
import com.rh.note.config.BeanConfig;
import com.rh.note.constant.AdocFilePathEnum;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.NoteException;
import com.rh.note.file.AdocFile;
import com.rh.note.grammar.ITitleGrammar;
import com.rh.note.grammar.IncludeGrammar;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.grammar.custom.TableCustomGrammar;
import com.rh.note.view.BasePanelView;
import com.rh.note.view.EditAreaView;
import com.rh.note.view.InputWindowRunView;
import com.rh.note.view.ModelView;
import com.rh.note.view.RootNodeRunView;
import com.rh.note.view.TextPaneRunView;
import com.rh.note.view.TextPaneScrollView;
import com.rh.note.view.TitleListView;
import com.rh.note.view.TitleNavigateButtonRunView;
import com.rh.note.view.TitleNavigateRunView;
import com.rh.note.view.TreeView;
import com.rh.note.view.WorkFrameRunView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Writer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
public class WorkViewAPI {
    /**
     * 生成include语法块
     */
    public AdocFile replaceIncludeBlock(String componentId) throws Exception {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return null;
        }
        String selectContent = textPane.getSelectContent();
        IncludeGrammar include = new IncludeGrammar().initByGrammar(selectContent, textPane.getFilePath());
        if (include == null) {
            return null;
        }
        // 替换为include语句
        textPane.replaceSelectContent(include.generateGrammar());
        return new AdocFile().init(include);
    }


    /**
     * 打开work_frame
     */
    public void initFrame() {
        WorkFrameRunView.create();
    }

    /**
     * 加载文件标题列表
     */
    public void loadTitleList(TitleGrammar rootTitle) {
        RootNodeRunView.create(rootTitle);
        RootNodeRunView rootNode = new RootNodeRunView().init();
        ModelView model = new ModelView().init();
        if (model == null) {
            return;
        }
        model.setRoot(rootNode);

        TreeView tree = new TreeView().init();
        if (tree == null) {
            return;
        }
        tree.expandAllRow();
    }

    /**
     * 展示已打开的编辑区,通过被选择的标题
     */
    public TitleGrammar showEditingAreaForExistingSelected() {
        TreeView tree = new TreeView().init();
        if (tree == null) {
            return null;
        }
        TitleGrammar title = tree.getSelectionUserObject();
        if (title == null) {
            return null;
        }
        log.info("TextAreaScrollView 将尝试获取已打开的编辑区");
        TextPaneScrollView textPaneScroll = new TextPaneScrollView().init(title.getFilePath());
        if (textPaneScroll == null) {
            return title;
        }
        EditAreaView editArea = new EditAreaView().init();
        editArea.show(textPaneScroll);
        return null;
    }

    /**
     * 展示已打开的编辑区,通过被导航栏标题
     */
    public TitleGrammar showEditingAreaForExistingSelected(TitleGrammar titleGrammar) {
        if (titleGrammar == null) {
            return null;
        }
        log.info("TextAreaScrollView 将尝试获取已打开的编辑区");
        TextPaneScrollView textPaneScroll = new TextPaneScrollView().init(titleGrammar.getFilePath());
        if (textPaneScroll == null) {
            return titleGrammar;
        }
        EditAreaView editArea = new EditAreaView().init();
        editArea.show(textPaneScroll);
        return null;
    }

    /**
     * 打开新的编辑区,通过被选择的标题
     */
    public void openNewEditingAreaForSelected(TitleGrammar titleGrammar, File file) {
        //创建添加编辑区控件
        TitleGrammar rootTitle = titleGrammar.getRootTitleOnFile();
        TextPaneRunView.create(rootTitle);
        TextPaneScrollView tps = new TextPaneScrollView().init(titleGrammar.getFilePath());
        EditAreaView editArea = new EditAreaView().init();
        tps.addTo(editArea);
        editArea.showLast();

        //将数据显示到编辑区
        TextPaneRunView textPane = new TextPaneRunView().initByFilePath(titleGrammar.getFilePath());
        textPane.read(file);
    }

    /**
     * include语法重命名
     */
    public String rename(String componentId) {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getLineContent();
        //获得include语法对象
        IncludeGrammar include = new IncludeGrammar().init(lineContent);
        if (include == null) {
            return null;
        }
        //弹窗修改为新名字
        String newTitleName = new InputWindowRunView(include.getTitleName()).getInputValue();
        //在编辑控件中修改为新的语法语句
        textPane.replaceSelectContent(newTitleName);
        return newTitleName;
    }

    /**
     * 显示或隐藏标题列表
     */
    public void hiddenOrShowTitleList() {
        new TitleListView().init().hiddenOrShow();
        new BasePanelView().init().refreshShow();
    }

    /**
     * 展示主窗口
     */
    public void showMainFrame() {
        new WorkFrameRunView().init().show();
    }

    /**
     * 遍历所有编辑控件,并将内容写入文件
     */
    public IForEach<String, Writer> saveAllEditContent() {
        return handler ->
                TextPaneRunView.forEach(view -> {
                    if (view == null) {
                        return;
                    }
                    Writer writer = handler.handle(view.getFilePath());
                    view.write(writer);
                });
    }

    /**
     * 选择include行
     */
    public boolean selectIncludeLine(String componentId) {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return false;
        }
        String lineContent = textPane.getLineContent();
        IncludeGrammar includeGrammar = new IncludeGrammar().init(lineContent);
        if (includeGrammar == null) {
            return false;
        }
        return textPane.selectIncludeFileName(includeGrammar);
    }

    /**
     * 选择include语法
     */
    public boolean selectIncludeGrammar(String componentId) {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return false;
        }
        String lineContent = textPane.getLineContent();
        IncludeGrammar includeGrammar = new IncludeGrammar().initByGrammar(lineContent, textPane.getFilePath());
        if (includeGrammar == null) {
            return false;
        }
        return textPane.selectCurrentLine();
    }

    /**
     * 获得include指向文件路径, 通过控件id
     */
    public String getIncludeFilePathByTextPaneId(String componentId) {
        if (StringUtils.isBlank(componentId)) {
            return null;
        }
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getLineContent();
        IncludeGrammar includeGrammar = new IncludeGrammar().init(lineContent);
        return includeGrammar.getTargetFileAbsolutePath();
    }

    /**
     * 关闭include指向文件编辑区
     */
    public void closeIncludeTargetTextPane(String absolutePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        int start = absolutePath.lastIndexOf("/adoc/") + 1;
        // 获得项目内的相对路径
        String filePath = absolutePath.substring(start);
        TextPaneScrollView textPaneScroll = new TextPaneScrollView().init(filePath);
        if (textPaneScroll == null) {
            return;
        }
        EditAreaView editArea = new EditAreaView().init();
        textPaneScroll.closeFrom(editArea);
    }

    /**
     * 判断当前行是否为include语法
     */
    public boolean isIncludeGrammarLine(String componentId) {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return false;
        }
        String lineContent = textPane.getLineContent();
        IncludeGrammar includeGrammar = new IncludeGrammar().initByGrammar(lineContent, textPane.getFilePath());
        return includeGrammar != null;
    }

    /**
     * 输入回车
     */
    public void insertEnter(ActionEvent e) {
        new DefaultEditorKit.InsertBreakAction().actionPerformed(e);
    }

    /**
     * ctrl + del 删除命令
     */
    public void ctrlDelete(ActionEvent e) {
        DefaultEditorKit defaultEditorKit = new DefaultEditorKit();
        Action action = Arrays.stream(defaultEditorKit.getActions())
                .filter(a -> DefaultEditorKit.deleteNextWordAction.equals(a.getValue(Action.NAME)))
                .findFirst()
                .orElseThrow(() -> new NoteException(ErrorCodeEnum.FAILED_TO_DELETE_INCLUDE_LINE));
        action.actionPerformed(e);
    }

    /**
     * 选择表格语法
     */
    public boolean selectTableGrammar(String componentId) {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return false;
        }
        String lineContent = textPane.getLineContent();
        TableCustomGrammar tableCustomGrammar = new TableCustomGrammar().init(lineContent);
        if (tableCustomGrammar == null) {
            return false;
        }
        return textPane.selectCurrentLine();
    }

    /**
     * 替换table语法块
     */
    public void replaceTableBlock(String componentId) {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return;
        }
        String selectContent = textPane.getSelectContent();
        TableCustomGrammar tableCustomGrammar = new TableCustomGrammar().init(selectContent);
        if (tableCustomGrammar == null) {
            return;
        }
        textPane.replaceSelectContent(tableCustomGrammar.generateGrammar());
    }

    /**
     * 安全删除include行
     */
    public String deleteInclude(String componentId) {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return null;
        }
        final String lineContent = textPane.getLineContent();
        final IncludeGrammar includeGrammar = new IncludeGrammar().init(lineContent);
        if (includeGrammar == null || !textPane.selectCurrentLine()) {
            return null;
        }
        textPane.replaceSelectContent("");
        return includeGrammar.getTargetFileAbsolutePath();
    }

    /**
     * 加载标题导航
     */
    public void loadTitleNavigate() {
        TreeView tree = new TreeView().init();
        if (tree == null) {
            return;
        }
        TitleGrammar title = tree.getSelectionUserObject();
        if (title == null) {
            return;
        }
        TitleNavigateRunView titleNavigate = new TitleNavigateRunView().init();
        titleNavigate.clearTitle();
        List<TitleGrammar> titles = title.getParentTitles();
        titles.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(TitleGrammar::getLevel))
                .forEach(tg -> {
                    TitleNavigateButtonRunView.create(tg);
                    TitleNavigateButtonRunView titleNavigateButton = new TitleNavigateButtonRunView().initByTitleName(tg.getName());
                    titleNavigate.add(titleNavigateButton);
                });
    }

    /**
     * 加载导航标题, 通过导航按钮
     */
    public void loadTitleNavigate(ITitleGrammar titleGrammar) {
        if (!(titleGrammar instanceof TitleGrammar)) {
            return;
        }
        TitleNavigateRunView titleNavigate = new TitleNavigateRunView().init();
        titleNavigate.clearTitle();
        List<TitleGrammar> titles = ((TitleGrammar) titleGrammar).getParentTitles();
        titles.stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(TitleGrammar::getLevel))
                .forEach(tg -> {
                    TitleNavigateButtonRunView.create(tg);
                    TitleNavigateButtonRunView titleNavigateButton = new TitleNavigateButtonRunView().initByTitleName(tg.getName());
                    titleNavigate.add(titleNavigateButton);
                });
    }

    /**
     * 下沉标题
     */
    public void sinkTitle(String componentId) {
        // 判断当前行是否为标题
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return;
        }
        String lineContent = textPane.getLineContent();
        TitleGrammar titleGrammar = new TitleGrammar().init(lineContent);
        if (titleGrammar == null) {
            return;
        }
        // 创建新的adocFile
        IAdocFile adocFile = AdocFilePathEnum.getAdocFile(textPane.getFilePath());
        new AdocFile()
                .unknown().include().title()
                .init(adocFile);
        // 获得标题的所有内容, 添加到adocFile
        // 将include引用替换为include指向文件的内容
        IAdocFile newAdocFile = adocFile.generateAdocFileByTitle(titleGrammar);
        if (newAdocFile == null) {
            return;
        }
        // 在当前编辑区替换标题内容为include语句
        List<IncludeGrammar> includeGrammars = adocFile.getIncludeOfTitle(titleGrammar);
        adocFile.title2Include(titleGrammar, newAdocFile.getFilePath());
        // 在下级目录中创建新的adocFile todo 这要放在外面做
        // 删除旧的标题下include指向的文件 todo 这要放在外面做
        // 保存编辑区内容
        saveAllEditContent();
        // 关闭删除的include指向的编辑区
        // 重新加载单行栏
        // 重新加载标题列表
        // 打开生成的新文件
    }

    private FileServiceAPI fileServiceAPI = BeanConfig.fileServiceApi;

    /**
     *进入include指向文件
     */
    public void enterInclude() {
        EditAreaView editArea = new EditAreaView().init();
        String filePath = editArea.getSelectTabFilePath();
        TextPaneRunView textPane = new TextPaneRunView().initByFilePath(filePath);
        if (textPane == null) {
            return;
        }
        IncludeGrammar includeGrammar = new IncludeGrammar().init(textPane.getLineContent());
        if (includeGrammar == null) {
            return;
        }
        TitleGrammar rootTitle = textPane.getRootTitle();
        TitleGrammar childrenTitle = rootTitle.getChildrenByFilePath(includeGrammar.getTargetFilePath2(), includeGrammar.getTargetTitleName());
        if (childrenTitle == null) {
            return;
        }
        TextPaneScrollView textPaneScroll = new TextPaneScrollView().init(childrenTitle.getFilePath());
        if (textPaneScroll == null) {
            TextPaneRunView.create(childrenTitle);
            textPaneScroll = new TextPaneScrollView().init(childrenTitle.getFilePath());
            File file = fileServiceAPI.readTitleFileContent(childrenTitle.getAbsolutePath());
            TextPaneRunView tp = new TextPaneRunView().initByFilePath(childrenTitle.getFilePath());
            tp.read(file);
            textPaneScroll.addTo(editArea);
        }
        editArea.show(textPaneScroll);
        loadTitleNavigate(childrenTitle);
    }

    /**
     * 加载导航栏, 通过选择tab
     */
    public TitleGrammar getTitleGrammarBySelectedTab() {
        EditAreaView editArea = new EditAreaView().init();
        String filePath = editArea.getSelectTabFilePath();
        TextPaneRunView textPane = new TextPaneRunView().initByFilePath(filePath);
        if (textPane == null) {
            return null;
        }
        return textPane.getRootTitle();
    }
}
