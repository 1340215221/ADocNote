package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.api.FileServiceApi;
import com.rh.note.api.WorkViewApi;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * 工作窗口 入口
 */
@Setter
public class WorkAction implements IWorkAction {

    private WorkViewApi workViewApi;
    private FileServiceApi fileServiceApi;

    /**
     * 启动窗口
     */
    public void startFrame(@NotNull ClickedHistoryProjectListAO ao) {
        ao.checkRequiredItems();
        fileServiceApi
    }
}
