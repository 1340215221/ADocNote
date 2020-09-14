package com.rh.note.api;

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
    public void loadTitleTreeData() {
    }

    /**
     * 显示窗口
     */
    public void showFrame() {
        new WorkFrameView().init().show();
    }
}
