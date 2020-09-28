package com.rh.note.api;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.view.ProManageFrameView;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.JList;

/**
 * 项目管理
 */
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
}
