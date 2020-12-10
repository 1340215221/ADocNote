package com.rh.note.view;

import com.rh.note.builder.HistoryProListBuilder;
import com.rh.note.common.ISingletonView;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.JList;

/**
 * 历史项目列表
 */
public class HistoryProListView extends ISingletonView<HistoryProListBuilder, JList<RecentlyOpenedRecordVO>> {

    @Override
    public @NotNull HistoryProListView init() {
        return super.init();
    }

    private @NotNull JList<RecentlyOpenedRecordVO> historyProList() {
        return super.getComponent(HistoryProListBuilder::getProList);
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
