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
     * 为窗口创建一个容器
     */
    public void loadNewFrameContext() {
        frameContextApi.loadContext(new LoadContextAO(FrameCategoryEnum.PRO_MANAGE));
    }

    /**
     * 点击项目列表
     */
    public void clickedProjectList(@NonNull ClickedProjectListAO ao) {
        //todo
        System.out.println(ao.getProPath());
    }
}
