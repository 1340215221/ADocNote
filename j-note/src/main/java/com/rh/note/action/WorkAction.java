package com.rh.note.action;

import com.rh.note.api.FileService;
import com.rh.note.api.WorkViewService;
import com.rh.note.config.WorkActionBeanClassConfig;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import lombok.Setter;

/**
 * 工作窗口操作入口
 */
@Setter
public class WorkAction implements WorkActionBeanClassConfig {

    private FileService fileService;
    private WorkViewService workViewService;

    @Override
    public void rename(AdocFile adocFile) {
    }

    @Override
    public void sinkTitle(AdocFile adocFile) {
    }

    @Override
    public void deleteInclude(AdocFile adocFile) {
    }

    @Override
    public void clickedNavigateButton(TitleLine titleLine) {
    }

    @Override
    public void loadTitleNavigateOnSelectedTab() {
    }

    @Override
    public void saveAllEdited() {
    }

    @Override
    public void openIncludeFile() {
    }

    /**
     * 打开窗口
     */
    public void startFrame() {
        TitleLine rootTitle = fileService.readAllTitle();
        if (rootTitle == null) {
            return;
        }
        workViewService.initFrame();
        workViewService.loadTitleTreeData();
        workViewService.showFrame();
    }

    @Override
    public void generateIncludeBlock(AdocFile adocFile) {
    }

    @Override
    public void generateTableBlock(AdocFile adocFile) {
    }

    @Override
    public void openAdocFileBySelectedNode() {
    }
}
