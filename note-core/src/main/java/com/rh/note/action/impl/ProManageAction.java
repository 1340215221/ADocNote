package com.rh.note.action.impl;

import com.rh.note.action.IProManageAction;
import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.api.FileServiceApi;
import com.rh.note.api.ProManageLoaderApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 项目管理 入口
 */
@Component
public class ProManageAction implements IProManageAction {

    @Autowired
    private ProManageViewApi proManageViewApi;
    @Autowired
    private FileServiceApi fileServiceApi;
    @Autowired
    private ProManageLoaderApi proManageLoaderApi;
    @Autowired
    private WorkAction workAction;

    @Override
    public void openProjectByPath(@NotNull ClickedHistoryProjectListAO ao) {
        ao.checkRequiredItems();
        fileServiceApi.setProjectPath(ao);
        workAction.initFrame();
        proManageViewApi.closeFrame();
        workAction.showFrame();
    }

    /**
     * 启动窗口
     */
    public void startFrame() {
        RecentlyOpenedRecordVO[] voArr = fileServiceApi.getHistoryOpenRecords();
        proManageLoaderApi.loadContext();
        proManageLoaderApi.loadComponent();
        proManageViewApi.startFrame(voArr);
    }
}
