package com.rh.note.action;

import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.ao.OpenAdocFileByFilePathAO;
import com.rh.note.ao.SaveTextPaneFileByFilePathAO;
import com.rh.note.api.FileApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.config.KeymapConfig;
import com.rh.note.line.TitleLine;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.event.MouseEvent;
import java.util.List;

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
     * 判断是否有被选中的编辑区
     */
    public boolean hasTextPaneSelected() {
        return workViewApi.hasTextPaneSelected();
    }

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
    public @Nullable OpenAdocFileByFilePathAO isExistFileBySelectedNode() {
        TitleLine titleLine = workViewApi.getTitleLineBySelectedNode();
        if (titleLine == null || titleLine.getBeanPath() == null) {
            return null;
        }
        boolean isAdocFile = fileApi.checkIsAdocFile(titleLine.getBeanPath().getAbsolutePath());
        if (!isAdocFile) {
            return null;
        }
        OpenAdocFileByFilePathAO ao = new OpenAdocFileByFilePathAO();
        ao.copy(titleLine);
        return ao;
    }

    /**
     * 判断存在被打开的编辑区
     */
    public @Nullable SaveTextPaneFileByFilePathAO hasTextPaneOpened() {
        List<String> list = workViewApi.getFilePathsOfTextPaneByTabbedPane();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        SaveTextPaneFileByFilePathAO ao = new SaveTextPaneFileByFilePathAO();
        ao.copy(list);
        return ao;
    }

    /**
     * 判断存在没有被选择的编辑区
     */
    public boolean hasTextPaneNotSelected() {
        return workViewApi.hasTextPaneNotSelected();
    }

    /**
     * 判断是ctrl + 鼠标左键
     */
    public boolean isCtrlLeftClick(MouseEvent event) {
        return keymap.ctrlLeftClick(event);
    }
}
