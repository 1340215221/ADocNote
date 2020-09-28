package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.api.FileServiceApi;
import com.rh.note.api.ProManageViewApi;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * 项目管理 入口
 */
@Setter
public class ProManageAction implements IProManageAction {

    private ProManageViewApi proManageViewApi;
    private FileServiceApi fileServiceApi;
    private WorkAction workAction;

    @Override
    public void openProjectByPath(@NotNull ClickedHistoryProjectListAO ao) {
        ao.checkRequiredItems();
        fileServiceApi.setProjectPath(ao);
        workAction.initFrame(ao);
        proManageViewApi.closeFrame();
        workAction.showFrame();
    }
}
