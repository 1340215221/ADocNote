package com.rh.note.api;

import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.common.IForEach;
import com.rh.note.file.AdocFile;
import com.rh.note.grammar.IncludeGrammar;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.view.BasePanelView;
import com.rh.note.view.EditAreaView;
import com.rh.note.view.InputWindowRunView;
import com.rh.note.view.ModelView;
import com.rh.note.view.RootNodeRunView;
import com.rh.note.view.TextAreaRunView;
import com.rh.note.view.TextAreaScrollView;
import com.rh.note.view.TextPaneRunView;
import com.rh.note.view.TextPaneScrollView;
import com.rh.note.view.TitleListView;
import com.rh.note.view.TreeView;
import com.rh.note.view.WorkFrameRunView;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.Writer;

@Slf4j
public class WorkViewAPI {
    /**
     * 判断光标所在行是否为include语句
     * todo 可抽取重复的部分, 调整代码顺序
     */
    public IncludeGrammar matchIncludeForTextLine(String componentId) throws Exception {
        TextAreaRunView textArea = new TextAreaRunView().init(componentId);
        if (textArea == null) {
            return null;
        }
        String lineContent = textArea.getLineContent();
        return new IncludeGrammar().initByGrammar(lineContent, textArea.getFilePath());
    }

    /**
     * 生成include语法块
     */
    public AdocFile generateIncludeBlock(String componentId) throws Exception {
        TextAreaRunView textArea = new TextAreaRunView().init(componentId);
        if (textArea == null) {
            return null;
        }
        String lineContent = textArea.getLineContent();
        IncludeGrammar include = new IncludeGrammar().initByGrammar(lineContent, textArea.getFilePath());
        if (include == null) {
            return null;
        }
        // 替换为include语句
        textArea.replaceIncludeGrammar(include);
        return new AdocFile().init(include);
    }
    /**
     * 生成include语法块
     */
    public AdocFile generateIncludeBlock2(String componentId) throws Exception {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return null;
        }
        String lineContent = textPane.getLineContent();
        IncludeGrammar include = new IncludeGrammar().initByGrammar(lineContent, textPane.getFilePath());
        if (include == null) {
            return null;
        }
        // 替换为include语句
        textPane.replaceIncludeGrammar(include);
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
        TextAreaScrollView textAreaScroll = new TextAreaScrollView().init(title.getFilePath());
        if (textAreaScroll == null) {
            return title;
        }
        EditAreaView editArea = new EditAreaView().init();
        editArea.show(textAreaScroll);
        return null;
    }

    /**
     * 展示已打开的编辑区,通过被选择的标题
     */
    public TitleGrammar showEditingAreaForExistingSelected2() {
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
//        editArea.show(textPaneScroll.getId()); todo
        return null;
    }

    /**
     * 打开新的编辑区,通过被选择的标题
     */
    public void openNewEditingAreaForSelected(String filePath, File file) {
        //创建添加编辑区控件
        TextAreaRunView.create(filePath);
        TextAreaScrollView tas = new TextAreaScrollView().init(filePath);
        EditAreaView editArea = new EditAreaView().init();
        tas.addTo(editArea, file);
        editArea.showLast();

        //将数据显示到编辑区
        TextAreaRunView textArea = new TextAreaRunView().initByFilePath(filePath);
        textArea.read(file);
    }

    /**
     * 打开新的编辑区,通过被选择的标题
     */
    public void openNewEditingAreaForSelected2(String filePath, File file) {
        //创建添加编辑区控件
        TextPaneRunView.create(filePath);
        TextPaneScrollView tps = new TextPaneScrollView().init(filePath);
        EditAreaView editArea = new EditAreaView().init();
        tps.addTo(editArea);
        editArea.showLast();

        //将数据显示到编辑区
        TextPaneRunView textPane = new TextPaneRunView().initByFilePath(filePath);
        textPane.read(file);
    }

    /**
     * include语法重命名
     */
    public void rename(String componentId) throws Exception {
        TextAreaRunView textArea = new TextAreaRunView().init(componentId);
        if (textArea == null) {
            return;
        }
        String lineContent = textArea.getLineContent();
        //获得include语法对象
        IncludeGrammar include = new IncludeGrammar().init(lineContent);
        if (include == null) {
            return;
        }
        //弹窗修改为新名字
        String newTitleName = new InputWindowRunView(include.getTitleName()).getInputValue();
        //在编辑控件中修改为新的语法语句
        textArea.replaceName(include.getTitleName(), newTitleName);
    }

    /**
     * include语法重命名
     */
    public void rename2(String componentId) throws Exception {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return;
        }
        String lineContent = textPane.getLineContent();
        //获得include语法对象
        IncludeGrammar include = new IncludeGrammar().init(lineContent);
        if (include == null) {
            return;
        }
        //弹窗修改为新名字
        String newTitleName = new InputWindowRunView(include.getTitleName()).getInputValue();
        //在编辑控件中修改为新的语法语句
        textPane.replaceName(include.getTitleName(), newTitleName);
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
                TextAreaRunView.forEach(view -> {
                    if (view == null) {
                        return;
                    }
                    Writer writer = handler.handle(view.getFilePath());
                    view.write(writer);
                });
    }

    /**
     * 遍历所有编辑控件,并将内容写入文件
     */
    public IForEach<String, Writer> saveAllEditContent2() {
        return handler ->
                TextPaneRunView.forEach(view -> {
                    if (view == null) {
                        return;
                    }
                    Writer writer = handler.handle(view.getFilePath());
                    view.write(writer);
                });
    }
}
