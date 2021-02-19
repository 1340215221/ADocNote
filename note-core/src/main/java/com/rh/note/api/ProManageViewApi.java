package com.rh.note.api;

import com.rh.note.constants.PromptMessageEnum;
import com.rh.note.view.ConfirmDialogView;
import com.rh.note.view.ProListView;
import com.rh.note.vo.FindSelectedProPathVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * 项目管理窗口操作 接口
 */
@Component
public class ProManageViewApi {
    /**
     * 获得项目列表中被选择的项
     */
    public @NotNull FindSelectedProPathVO getSelectedProPathInProList() {
        ProListView view = new ProListView().init();
        FindSelectedProPathVO vo = new FindSelectedProPathVO();
        vo.copy(view);
        return vo;
    }

    /**
     * 弹窗提示git操作失败
     */
    public boolean promptGitOperationFailed() {
        ConfirmDialogView dialogView = new ConfirmDialogView().init(PromptMessageEnum.GIT_OPERATION_FAILED);
        return dialogView.isConfirm();
    }
}
