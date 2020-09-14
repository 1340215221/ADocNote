package com.rh.note.action;

import com.rh.note.api.FileService;
import com.rh.note.api.ProManageViewAPI;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.Setter;

/**
 * 项目管理入口
 */
@Setter
public class ProManageAction implements IProjectManagementAction {

    private FileService fileService;
    private ProManageViewAPI proManageViewAPI;
    private WorkAction workAction;

    /**
     * 启动窗口
     */
    public void startFrame() {
        RecentlyOpenedRecordVO[] voArr = fileService.readHistoryProject();
        proManageViewAPI.startFrame();
        proManageViewAPI.loadHistoryProData(voArr);
    }

    @Override
    public void openSelectedHistoryProject() {
        String projectPath = proManageViewAPI.selectedProjectOperation();
        fileService.selectProject(projectPath);
        proManageViewAPI.closeFrame();
        workAction.startFrame();
    }

    @Override
    public void openDialogForSelectProject() {

    }

    @Override
    public void openProjectByDirectoryPath(String filePath) {

    }
}
