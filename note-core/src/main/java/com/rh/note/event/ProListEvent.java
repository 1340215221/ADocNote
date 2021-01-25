package com.rh.note.event;

import com.rh.note.action.OperationAction;
import com.rh.note.action.ProManageAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.common.BaseEvent;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.vo.ProItemVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.MouseEvent;

/**
 * 项目管理--项目列表 事件
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
public class ProListEvent extends BaseEvent {

    @Autowired
    private OperationAction operationAction;
    @Autowired
    private ProManageAction proManageAction;

    /**
     * 点击项目列表
     */
    public void clicked_project_list(MouseEvent mouseEvent) {
        ClickedProjectListAO ao = operationAction.clickedProjectList(mouseEvent);
        if (ao == null) {
            return;
        }
        proManageAction.openAdocProject(ao);
    }

    /**
     * 加载项目列表内容
     */
    public ProItemVO[] load_pro_list_content() {
        // todo
        return new ProItemVO[]{
                new ProItemVO().setProjectName("Java笔记").setProjectPath("/my_code/InterviewNote")
        };
    }
}
