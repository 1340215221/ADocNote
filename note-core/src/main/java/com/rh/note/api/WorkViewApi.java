package com.rh.note.api;

import com.rh.note.ao.TextPaneFileWritersAO;
import com.rh.note.exception.UnknownBusinessSituationException;
import com.rh.note.line.TitleLine;
import com.rh.note.path.FileBeanPath;
import com.rh.note.view.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * 工作窗口操作 接口
 */
@Component
public class WorkViewApi {

    /**
     * 更新根标题
     */
    public void updateRootNode(TitleLine rooTitle) {
        if (rooTitle == null) {
            return;
        }
        // 删除旧节点
        RootTitleNodeView oldNodeView = new RootTitleNodeView().init();
        if (oldNodeView != null) {
            oldNodeView.deleteAllNote();
        }
        // 更新新节点
        RootTitleNodeView nodeView = new RootTitleNodeView().create(rooTitle);
        TreeModelView modelView = new TreeModelView().init();
        modelView.setRootNode(nodeView);
        new TitleTreeView().init().expandAllNode();
    }

    /**
     * 显示已打开文件
     */
    public void showOpenedFileByFilePath(String filePath) {
        AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
        if (textPaneView == null) {
            return;
        }
        TextScrollPaneView scrollPaneView = new TextScrollPaneView().init(filePath);
        if (scrollPaneView == null) {
            throw new UnknownBusinessSituationException();
        }
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        tabbedPaneView.show(scrollPaneView);
    }

    /**
     * 判断adoc文件已打开
     */
    public boolean checkIsOpenedFile(String filePath) {
        return StringUtils.isNotBlank(filePath) && new AdocTextPaneView().init(filePath) != null;
    }

    /**
     * 保存编辑区内容,通过文件路径
     */
    public void saveTextPaneFileByFilePaths(TextPaneFileWritersAO ao) {
        if (ao == null || ao.checkRequiredParamsError()) {
            return;
        }
        ao.forEach((String filePath, Writer writer) -> {
            AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
            if (textPaneView == null) {
                return;
            }
            textPaneView.writerToFile(writer);
        });
    }

    /**
     * 生成编辑区
     */
    public void createAdocTextPane(FileBeanPath beanPath, Reader reader) {
        if (beanPath == null) {
            return;
        }
        // 创建编辑区
        AdocTextPaneView textPaneView = new AdocTextPaneView().create(beanPath);
        // 加载编辑区内容
        textPaneView.initContent(reader);
        // 添加到选项卡
        TextScrollPaneView scrollPaneView = new TextScrollPaneView().init(beanPath.getFilePath());
        if (scrollPaneView == null) {
            throw new UnknownBusinessSituationException();
        }
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        tabbedPaneView.add(scrollPaneView);
        tabbedPaneView.show(scrollPaneView);
    }

    /**
     * 获得被选择节点的标题对象
     */
    public @Nullable TitleLine getTitleLineBySelectedNode() {
        TitleTreeView treeView = new TitleTreeView().init();
        return treeView.getTitleLineBySelectedNode();
    }

    /**
     * 获得被选择的编辑区对应的文件地址
     */
    public @Nullable String getFilePathOfTextPaneSelected() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        return tabbedPaneView != null ? tabbedPaneView.getFilePathOfTextPaneSelected() : null;
    }

    /**
     * 获得选项卡中的编辑控件对应的文件地址
     */
    public @Nullable List<String> getFilePathsOfTextPaneByTabbedPane() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        // 在非编辑窗口使用ctrl + s 时, 编辑区选项卡会为空
        if (tabbedPaneView == null) {
            return null;
        }
        return tabbedPaneView.getFilePathsOfTextPane();
    }

    /**
     * 判断是否有被选中的编辑区
     */
    public boolean hasTextPaneSelected() {
        TabbedPaneView tabbedPaneView = new TabbedPaneView().init();
        return tabbedPaneView != null && tabbedPaneView.existSelected();
    }

    /**
     * 关闭编辑区
     */
    public void closeTextPaneByFilePaths(List<String> filePaths) {
        if (CollectionUtils.isEmpty(filePaths)) {
            return;
        }
        filePaths.forEach(filePath -> {
            AdocTextPaneView textPaneView = new AdocTextPaneView().init(filePath);
            if (textPaneView == null) {
                return;
            }
            textPaneView.close();
        });
    }
}
