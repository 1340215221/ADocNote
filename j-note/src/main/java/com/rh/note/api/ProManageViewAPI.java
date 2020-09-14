package com.rh.note.api;

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

    /**
     * 选择的项目操作
     */
    public String selectedProjectOperation() {
        HistoryProListView historyProList = new HistoryProListView().init();
        return historyProList.getSelectedProjectPath();
    }

    /**
     * 关闭窗口
     */
    public void closeFrame() {
        new ProManageFrameView().init().close();
    }
}
