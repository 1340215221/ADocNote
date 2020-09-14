package com.rh.note.view;

import com.rh.note.builder.HistoryProjectListBuilder;
import com.rh.note.util.Init;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.JList;

/**
 * 历史项目列表
 */
public class HistoryProListView extends Init<JList<RecentlyOpenedRecordVO>> {
    public HistoryProListView init() {
        return super.init(HistoryProjectListBuilder.id());
    }

    /**
     * 加载数据
     */
    public void loadData(RecentlyOpenedRecordVO[] voArr) {
        if (ArrayUtils.isEmpty(voArr)) {
            return;
        }
        historyProList().setListData(voArr);
    }

    private JList<RecentlyOpenedRecordVO> historyProList() {
        return getBean();
    }

    /**
     * 获得选择的项目
     */
    public String getSelectedProjectPath() {
        RecentlyOpenedRecordVO vo = historyProList().getSelectedValue();
        if (vo == null || StringUtils.isBlank(vo.getProjectPath())) {
            return null;
        }
        return vo.getProjectPath();
    }
}
