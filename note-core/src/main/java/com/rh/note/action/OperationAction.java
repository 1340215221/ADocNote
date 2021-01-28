package com.rh.note.action;

import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.ao.OpenAdocFileByTitleNodeAO;
import com.rh.note.api.FileApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.config.KeymapConfig;
import com.rh.note.line.TitleLine;
import com.rh.note.view.TitleTreeView;
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
        String proPath = proManageViewApi.getSelectedProPathInProList();
        CheckIsAdocProjectAO ao = new CheckIsAdocProjectAO(proPath);
        return fileApi.checkIsAdocProject(ao);
    }

    /**
     * 判断存在文件, 通过被选中的节点
     */
    public OpenAdocFileByTitleNodeAO isExistFileBySelectedNode() {
        TitleTreeView treeView = new TitleTreeView().init();
        TitleLine titleLine = treeView.getTitleLineBySelectedNode();
        workViewApi.isExistFileBySelectedNode();
    }
}
