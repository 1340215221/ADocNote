package com.rh.note.api;

import com.rh.note.builder.HistoryProjectListBuilder;
import com.rh.note.util.Init;
import com.rh.note.view.HistoryProListView;
import com.rh.note.view.ProManageFrameView;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.ArrayUtils;

/**
 * 项目管理窗口服务
 */
public class ProManageViewAPI {
    /**
     * 启动窗口
     */
    public void startFrame() {
        ProManageFrameView.create();
    }

    /**
     * 加载历史项目数据
     */
    public void loadHistoryProData(RecentlyOpenedRecordVO[] voArr) {
        if (ArrayUtils.isEmpty(voArr)) {
            return;
        }
        new HistoryProListView().init().loadData(voArr);
        new ProManageFrameView().init().show();
    }
}
