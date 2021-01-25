package com.rh.note.action;

import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.ao.LoadContextAO;
import com.rh.note.api.FrameContextApi;
import com.rh.note.constants.FrameCategoryEnum;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 项目管理 入口
 */
@Component
public class ProManageAction {

    @Autowired
    private FrameContextApi frameContextApi;

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
}
