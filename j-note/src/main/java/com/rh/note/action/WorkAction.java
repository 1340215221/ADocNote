package com.rh.note.action;

import com.rh.note.api.FileServiceApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.line.TitleLine;
import lombok.Setter;

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
    public void initFrame() {
        TitleLine rootTitle = fileServiceApi.readAllTitleByProjectPath();
        if (rootTitle == null) {
            throw new ApplicationException(ErrorCodeEnum.CANNOT_OPEN_A_PROJECT_WITHOUT_A_TITLE);
        }
        workViewApi.initFrame();
        workViewApi.loadTitleTree(rootTitle);
    }

    /**
     * 显示窗口
     */
    public void showFrame() {
        workViewApi.showFrame();
    }
}
