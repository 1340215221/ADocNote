package com.rh.note.api;

import com.rh.note.frame.WorkFrame;
import com.rh.note.line.TitleLine;
import com.rh.note.view.RootTitleNodeView;
import com.rh.note.view.TitleTreeModelView;
import com.rh.note.view.TitleTreeView;
import com.rh.note.view.WorkFrameView;

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
}
