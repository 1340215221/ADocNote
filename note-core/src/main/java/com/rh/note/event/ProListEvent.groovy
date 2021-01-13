package com.rh.note.event

import com.rh.note.action.OperationAction
import com.rh.note.action.ProManageAction
import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.vo.ProItemVO
import org.springframework.beans.factory.annotation.Autowired

import java.awt.event.MouseEvent

/**
 * 项目管理--项目列表 事件
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
class ProListEvent {

    @Autowired
    private OperationAction operationAction
    @Autowired
    private ProManageAction proManageAction

    /**
     * 点击项目列表
     */
    void clicked_project_list(MouseEvent mouseEvent) {
        //todo
    }

    /**
     * 加载项目列表内容
     */
    ProItemVO[] loadProListContent() {
        // todo
        [
                new ProItemVO(projectName: 'Java笔记',
                projectPath: '/my_code/ADocNote'),
        ]
    }
}
