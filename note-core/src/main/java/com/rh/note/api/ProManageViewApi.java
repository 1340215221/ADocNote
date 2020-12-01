package com.rh.note.api;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.view.HistoryProListView;
import com.rh.note.view.ProManageFrameView;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import javax.swing.JList;

/**
 * 项目管理
 */
@Component
public class ProManageViewApi {

    public @Nullable ClickedHistoryProjectListAO getSelectedHistoryProjectAO(JList<RecentlyOpenedRecordVO> jList) {
        if (jList == null) {
            return null;
        }
        RecentlyOpenedRecordVO vo = jList.getSelectedValue();
        if (vo == null || StringUtils.isBlank(vo.getProjectPath())) {
            return null;
        }
        return new ClickedHistoryProjectListAO().setProjectPath(vo.getProjectPath());
    }
    /**
     * 关闭窗口
     */
    public void closeFrame() {
        new ProManageFrameView().init().close();
    }

    /**
     * 启动窗口, 加载历史打开记录
     */
    public void startFrame(RecentlyOpenedRecordVO[] voArr) {
        ProManageFrameView.create();
        new HistoryProListView().init().loadData(voArr);
        new ProManageFrameView().init().show();
    }
}
