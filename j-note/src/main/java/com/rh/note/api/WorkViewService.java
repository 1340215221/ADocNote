package com.rh.note.api;

import com.rh.note.line.TitleLine;
import com.rh.note.view.RootTitleNodeView;
import com.rh.note.view.TitleTreeModelView;
import com.rh.note.view.TitleTreeView;
import com.rh.note.view.WorkFrameView;

/**
 * 工作窗口服务
 */
public class WorkViewService {
    /**
     * 初始化窗口
     */
    public void initFrame() {
        WorkFrameView.create();
    }

    /**
     * 加载标题树的数据
     */
    public void loadTitleTreeData(TitleLine rootTitle) {
        RootTitleNodeView.create(rootTitle);
        RootTitleNodeView rootTitleNode = new RootTitleNodeView().init();
        TitleTreeModelView titleTreeModel = new TitleTreeModelView().init();
        titleTreeModel.setRoot(rootTitleNode);
        TitleTreeView titleTree = new TitleTreeView().init();
        titleTree.expandAllRow();
    }

    /**
     * 显示窗口
     */
    public void showFrame() {
        new WorkFrameView().init().show();
    }
}
