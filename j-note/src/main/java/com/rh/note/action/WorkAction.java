package com.rh.note.action;

import com.rh.note.api.FileAPIService;
import com.rh.note.api.WorkViewAPIService;
import com.rh.note.config.WorkActionBeanClassConfig;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import lombok.Setter;

/**
 * 工作窗口操作入口
 */
@Setter
public class WorkAction implements WorkActionBeanClassConfig {

    private FileAPIService fileAPIService;
    private WorkViewAPIService workViewAPIService;

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
