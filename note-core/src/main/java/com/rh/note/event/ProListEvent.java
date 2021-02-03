package com.rh.note.event;

import com.rh.note.action.OperationAction;
import com.rh.note.action.ProManageAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.vo.ProItemVO;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.event.MouseEvent;

/**
 * 项目管理--项目列表 事件
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
public class ProListEvent {

    @Autowired
    private OperationAction operationAction;
    @Autowired
    private ProManageAction proManageAction;

    /**
     * 点击项目列表
     */
    public void clicked_project_list(@NonNull MouseEvent mouseEvent) {
        boolean isDoubleClick = operationAction.isDoubleClick(mouseEvent);
        if (isDoubleClick) {
            proManageAction.openAdocProjectSelected();
        }
    }

    /**
     * 加载项目列表内容
     */
    public @NotNull ProItemVO[] load_pro_list_content() {
        return proManageAction.getProPathList();
    }
}
