package com.rh.note.action;

import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.api.FileApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.config.KeymapConfig;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;

@Component
public class OperationAction {

    @Autowired
    private KeymapConfig keymap;
    @Autowired
    private WorkViewApi workViewApi;
    @Autowired
    private ProManageViewApi proManageViewApi;
    @Autowired
    private FileApi fileApi;

    /**
     * 点击项目列表
     */
    public ClickedProjectListAO clickedProjectList(@NonNull MouseEvent mouseEvent) {
        if (!keymap.doubleClick(mouseEvent)) {
            return null;
        }
        // 获得被选择的项
        String proPath = proManageViewApi.getSelectedItemInProList();
        CheckIsAdocProjectAO ao = new CheckIsAdocProjectAO();
        return fileApi.checkIsAdocProject(ao);
    }
}
