package com.rh.note.api;

import com.rh.note.frame.WorkFrame;
import com.rh.note.view.WorkFrameView;
import com.rh.note.vo.TitleLineVO;

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
    public void loadTitleTree(TitleLineVO rootTitle) {
        if (rootTitle == null) {
            return;
        }

        // todo
    }
}
