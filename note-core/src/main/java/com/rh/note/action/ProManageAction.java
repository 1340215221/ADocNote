package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.api.FileServiceApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.NotNull;

/**
 * 项目管理 入口
 */
@Setter
@Accessors(chain = true)
public class ProManageAction implements IProManageAction {

    private ProManageViewApi proManageViewApi;
    private FileServiceApi fileServiceApi;
    private WorkAction workAction;

    @Override
    public void openProjectByPath(@NotNull ClickedHistoryProjectListAO ao) {
        ao.checkRequiredItems();
        fileServiceApi.setProjectPath(ao);
        workAction.initFrame();
        proManageViewApi.closeFrame();
        workAction.showFrame();
    }

    /**
     * 启动窗口
     */
    public void startFrame() {
        RecentlyOpenedRecordVO[] voArr = fileServiceApi.getHistoryOpenRecords();
        proManageViewApi.startFrame(voArr);
    }
}
