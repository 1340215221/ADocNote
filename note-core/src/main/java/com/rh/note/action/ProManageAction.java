package com.rh.note.action;

import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.ao.LoadContextAO;
import com.rh.note.api.FrameContextApi;
import com.rh.note.common.ViewThreadContext;
import com.rh.note.config.UserProPathConfig;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.vo.ProItemVO;
import lombok.NonNull;
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

    /**
     * 打开项目管理窗口
     */
    public void openProMangeFrame() {
        frameContextApi.loadContext(new LoadContextAO(FrameCategoryEnum.PRO_MANAGE));
    }

    /**
     * 在项目列表中打开一个项目
     */
    public void openAdocProject(@NonNull ClickedProjectListAO ao) {
        LoadContextAO loadContextAO = ao.copyToLoadContextAO();
        frameContextApi.loadContext(loadContextAO);
        frameContextApi.closeContext();
    }

    /**
     * 获得项目地址
     */
    public @NotNull ProItemVO[] getProPathList() {
        return userProPathConfig.getProPathList();
    }
}
