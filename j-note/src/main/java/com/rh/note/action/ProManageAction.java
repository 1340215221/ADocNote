package com.rh.note.action;

import com.rh.note.api.FileService;
import com.rh.note.api.ProManageViewService;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.NonNull;
import lombok.Setter;

/**
 * 项目管理入口
 */
@Setter
public class ProManageAction implements IProjectManagementAction {

    private FileService fileService;
    private ProManageViewService proManageViewService;
    private WorkAction workAction;

    /**
     * 启动窗口
     */
    public void startFrame() {
        RecentlyOpenedRecordVO[] voArr = fileService.readHistoryProject();
        proManageViewService.startFrame();
        proManageViewService.loadHistoryProData(voArr);
    }

    @Override
    public void openSelectedHistoryProject(@NonNull String projectPath) {
        fileService.selectProject(projectPath);
        proManageViewService.closeFrame();
        workAction.startFrame();
    }

    @Override
    public String openDialogForSelectProject() {
        return proManageViewService.openDialogForSelectProject();
    }
}
