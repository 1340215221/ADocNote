package com.rh.note.api;

import com.rh.note.file.AdocFile;
import com.rh.note.file.ConfigFile;
import com.rh.note.grammar.IncludeGrammar;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.view.BasePanelView;
import com.rh.note.view.EditAreaView;
import com.rh.note.view.InputWindowRunView;
import com.rh.note.view.ModelView;
import com.rh.note.view.RootNodeRunView;
import com.rh.note.view.TextAreaRunView;
import com.rh.note.view.TextAreaScrollView;
import com.rh.note.view.TitleListView;
import com.rh.note.view.TreeView;
import com.rh.note.view.WorkFrameRunView;

import java.io.File;

public class WorkViewAPI {
    /**
     * 生成include语法块
     */
    public AdocFile generateIncludeBlock(String componentId, ConfigFile config) throws Exception {
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
        return include.generateAdocFile(config);
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
    public String showEditingAreaForExistingSelected() {
        TreeView tree = new TreeView().init();
        if (tree == null) {
            return null;
        }
        TitleGrammar title = tree.getSelectionUserObject();
        if (title == null) {
            return null;
        }
        TextAreaScrollView textAreaScroll = new TextAreaScrollView().init(title.getAbsolutePath());
        if (textAreaScroll == null) {
            return title.getAbsolutePath();
        }
        EditAreaView editArea = new EditAreaView().init();
        editArea.show(textAreaScroll.getId());
        return null;
    }

    /**
     * 打开新的编辑区,通过被选择的标题
     */
    public void openNewEditingAreaForSelected(String absolutePath, File file) {
        //创建添加编辑区控件
        TextAreaRunView.create(absolutePath);
        TextAreaScrollView tas = new TextAreaScrollView().init(absolutePath);
        EditAreaView editArea = new EditAreaView().init();
        tas.addTo(editArea);
        editArea.showLast();

        //将数据显示到编辑区
        TextAreaRunView textArea = new TextAreaRunView().initByFilePath(absolutePath);
        textArea.read(file);
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
}
