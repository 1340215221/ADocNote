package com.rh.note.api;

import com.rh.note.view.ProListView;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * 项目管理窗口操作 接口
 */
@Component
public class ProManageViewApi {
    /**
     * 获得项目列表中被选择的项
     */
    public @Nullable String getSelectedProPathInProList() {
        ProListView view = new ProListView().init();
        return view.getSelectProPath();
    }
}
