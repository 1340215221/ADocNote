package com.rh.note.api;

import com.rh.note.factory.frame.WorkFrameFactory;
import com.rh.note.model.component.BasePanelImpl;
import com.rh.note.model.component.EditAreaImpl;
import com.rh.note.model.component.ModelImpl;
import com.rh.note.model.component.RootNodeImpl;
import com.rh.note.model.component.TextAreaRunImpl;
import com.rh.note.model.component.TextAreaScrollImpl;
import com.rh.note.model.component.TitleListImpl;
import com.rh.note.model.component.TreeImpl;
import com.rh.note.model.file.AdocFile;
import com.rh.note.model.file.Config;
import com.rh.note.model.file.Title;
import com.rh.note.model.grammar.Include;
import com.rh.note.view.InputWindow;

import java.io.File;

public class WorkViewAPI {
    /**
     * 生成include语法块
     */
    public AdocFile generateIncludeBlock(String componentId, Config config) throws Exception {
        TextAreaRunImpl textArea = new TextAreaRunImpl().init(componentId);
        if (textArea == null) {
            return null;
        }
        String lineContent = textArea.getLineContent();
        Include include = new Include().initByGrammar(lineContent, textArea.getFilePath());
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
    public void openFrame() {
        new WorkFrameFactory().start();
    }

    /**
     * 加载文件标题列表
     */
    public void showTitleList(Title rootTitle) {
        RootNodeImpl.create(rootTitle);
        RootNodeImpl rootNode = new RootNodeImpl().init();
        ModelImpl model = new ModelImpl().init();
        if (model == null) {
            return;
        }
        model.setRoot(rootNode);

        TreeImpl tree = new TreeImpl().init();
        if (tree == null) {
            return;
        }
        tree.expandAllRow();
    }

    /**
     * 展示已打开的编辑区,通过被选择的标题
     */
    public String showEditingAreaForExistingSelected() {
        TreeImpl tree = new TreeImpl().init();
        if (tree == null) {
            return null;
        }
        Title title = tree.getSelectionUserObject();
        if (title == null) {
            return null;
        }
        TextAreaScrollImpl textAreaScroll = new TextAreaScrollImpl().init(title.getAbsolutePath());
        if (textAreaScroll == null) {
            return title.getAbsolutePath();
        }
        EditAreaImpl editArea = new EditAreaImpl().init();
        editArea.show(textAreaScroll.getId());
        return null;
    }

    /**
     * 打开新的编辑区,通过被选择的标题
     */
    public void openNewEditingAreaForSelected(String absolutePath, File file) {
        //创建添加编辑区控件
        TextAreaRunImpl.create(absolutePath);
        TextAreaScrollImpl tas = new TextAreaScrollImpl().init(absolutePath);
        EditAreaImpl editArea = new EditAreaImpl().init();
        tas.addTo(editArea);
        editArea.showLast();

        //将数据显示到编辑区
        TextAreaRunImpl textArea = new TextAreaRunImpl().initByFilePath(absolutePath);
        textArea.read(file);
    }

    /**
     * include语法重命名
     */
    public void rename(String componentId) throws Exception {
        TextAreaRunImpl textArea = new TextAreaRunImpl().init(componentId);
        if (textArea == null) {
            return;
        }
        String lineContent = textArea.getLineContent();
        //获得include语法对象
        Include include = new Include().init(lineContent);
        if (include == null) {
            return;
        }
        //弹窗修改为新名字
        String newTitleName = new InputWindow(include.getTitleName()).getInputValue();
        //在编辑控件中修改为新的语法语句
        textArea.replaceName(include.getTitleName(), newTitleName);
    }

    /**
     * 显示或隐藏标题列表
     */
    public void hiddenOrShowTitleList() {
        new TitleListImpl().init().hiddenOrShow();
        new BasePanelImpl().init().refreshShow();
    }

    /**
     * 刷新标题树
     */
    public void refreshTitleTree() {
        //todo
    }
}
