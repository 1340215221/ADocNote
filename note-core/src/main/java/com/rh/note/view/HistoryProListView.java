package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.HistoryProListBuilder;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.JList;

/**
 * 历史项目列表
 */
public class HistoryProListView extends Init<JList<RecentlyOpenedRecordVO>> {
    public @NotNull HistoryProListView init() {
        return super.init(HistoryProListBuilder.id());
    }

    private @NotNull JList<RecentlyOpenedRecordVO> historyProList() {
        return getBean();
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
}
