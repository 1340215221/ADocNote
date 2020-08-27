package com.rh.note.api;

import com.rh.note.common.IForEach;
import com.rh.note.file.AdocFile;
import com.rh.note.grammar.IncludeGrammar;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.view.BasePanelView;
import com.rh.note.view.EditAreaView;
import com.rh.note.view.InputWindowRunView;
import com.rh.note.view.ModelView;
import com.rh.note.view.RootNodeRunView;
import com.rh.note.view.TextPaneRunView;
import com.rh.note.view.TextPaneScrollView;
import com.rh.note.view.TitleListView;
import com.rh.note.view.TreeView;
import com.rh.note.view.WorkFrameRunView;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Writer;
import java.util.regex.Pattern;

@Slf4j
public class WorkViewAPI {
    /**
     * 生成include语法块
     */
    public AdocFile generateIncludeBlock(String componentId) throws Exception {
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
     * 打开新的编辑区,通过被选择的标题
     */
    public void openNewEditingAreaForSelected(String filePath, File file) {
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
     * 输入回车
     */
    public void insertEnter(String componentId) throws Exception {
        TextPaneRunView textPane = new TextPaneRunView().init(componentId);
        if (textPane == null) {
            return;
        }
        textPane.insertEnter();
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
}
