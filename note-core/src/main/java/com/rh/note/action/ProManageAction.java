package com.rh.note.action;

import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.LoadContextAO;
import com.rh.note.api.FileApi;
import com.rh.note.api.FrameContextApi;
import com.rh.note.api.GitApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.app.config.UserProPathConfig;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.vo.FindSelectedProPathVO;
import com.rh.note.vo.ProItemVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 项目管理 入口
 */
@Slf4j
@Component
public class ProManageAction {

    @Autowired
    private FrameContextApi frameContextApi;
    @Autowired
    private UserProPathConfig userProPathConfig;
    @Autowired
    private ProManageViewApi proManageViewApi;
    @Autowired
    private FileApi fileApi;

    /**
     * 打开项目管理窗口
     */
    public void openProMangeFrame() {
        LoadContextAO ao = new LoadContextAO();
        ao.setFrameCategoryEnum(FrameCategoryEnum.PRO_MANAGE);
        frameContextApi.loadContext(ao);
    }

    /**
     * 打开被选择的adoc项目
     */
    public void openAdocProjectSelected() {
        FindSelectedProPathVO findSelectedProPathVO = proManageViewApi.getSelectedProPathInProList();
        CheckIsAdocProjectAO checkIsAdocProjectAO = findSelectedProPathVO.copyToCheckIsAdocProjectAO();
        boolean isAdocProPath = fileApi.checkIsAdocProject(checkIsAdocProjectAO);
        if (!isAdocProPath) {
            return;
        }
        LoadContextAO loadContextAO = findSelectedProPathVO.copyToLoadContextAO();
        if (loadContextAO == null) {
            return;
        }
        frameContextApi.loadContext(loadContextAO);
        frameContextApi.closeContext();
    }

    /**
     * 获得项目地址
     */
    public @NotNull ProItemVO[] getProPathList() {
        return userProPathConfig.getProPathList();
    }

    /**
     * 退出程序
     */
    public void exitApp() {
        frameContextApi.closeContext();
        frameContextApi.forceExitApp();
    }
}
