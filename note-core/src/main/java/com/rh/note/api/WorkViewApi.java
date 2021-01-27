package com.rh.note.api;

import com.rh.note.line.TitleLine;
import com.rh.note.view.RootTitleNodeView;
import com.rh.note.view.TitleTreeView;
import com.rh.note.view.TreeModelView;
import org.springframework.stereotype.Component;

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
}
