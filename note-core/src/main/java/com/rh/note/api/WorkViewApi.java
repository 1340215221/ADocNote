package com.rh.note.api;

import com.rh.note.exception.UnknownBusinessSituationException;
import com.rh.note.line.TitleLine;
import com.rh.note.path.FileBeanPath;
import com.rh.note.view.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.Reader;

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
    }
}