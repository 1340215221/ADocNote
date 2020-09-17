package com.rh.note.action;

import com.rh.note.ao.SyntaxAnalysisAO;
import com.rh.note.api.FileService;
import com.rh.note.api.WorkViewService;
import com.rh.note.config.WorkActionBeanClassConfig;
import com.rh.note.file.AdocFile;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TitleLine;
import com.rh.note.view.TextPaneView;
import lombok.Setter;

import java.io.File;

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
    public void loadTitleNavigateByTitle(TitleLine titleLine) {
        workViewService.loadTitleNavigateByTitle(titleLine);
    }

    @Override
    public void saveAllEdited() {
    }

    @Override
    public TitleLine getCursorTitleOfSelectedTab() {
        return workViewService.getCursorTitleOfSelectedTab();
    }

    @Override
    public void openTextPaneByTitle(TitleLine titleLine) {
        if (titleLine == null) {
            return;
        }
        AdocFile adocFile = titleLine.getAdocFile();
        TextPaneView textPane = workViewService.createNonExistentTextPane(adocFile);
        if (textPane == null) {
            return;
        }
        if (textPane.isBlank()) {
            File file = fileService.getFileByPath(adocFile.getAbsolutePath());
            workViewService.loadTextPaneData(textPane, file);
        }
        workViewService.showTextPane(adocFile.getFilePath());
        workViewService.positioningToTitleRow(titleLine);
    }

    @Override
    public void openTextPaneByInclude(IncludeLine includeLine) {
        if (includeLine == null) {
            return;
        }
        this.openTextPaneByTitle(includeLine.getTargetTitle());
    }

    @Override
    public void generateIncludeBlock(SyntaxAnalysisAO ao) {
        workViewService.generateIncludeBlock(ao);
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
        workViewService.loadTitleTreeData(rootTitle);
        workViewService.showFrame();
    }
}
