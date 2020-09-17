package com.rh.note.action;

import com.rh.note.ao.SyntaxAnalysisAO;
import com.rh.note.api.ProManageViewService;
import com.rh.note.api.WorkViewService;
import com.rh.note.base.BaseLine;
import com.rh.note.config.OperationActionBeanClassConfig;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TitleLine;
import lombok.Setter;

/**
 * 解析用户操作入口
 */
@Setter
public class OperationAction implements OperationActionBeanClassConfig {

    private ProManageViewService proManageViewService;
    private WorkViewService workViewService;

    @Override
    public boolean matchRename() {
        return false;
    }

    @Override
    public boolean matchSinkTitle() {
        return false;
    }

    @Override
    public boolean matchDeleteInclude() {
        return false;
    }

    @Override
    public String clickedHistoryProjectList() {
        return proManageViewService.getSelectedHistoryProjectPath();
    }

    @Override
    public TitleLine clickedTitleTreeNode() {
        return workViewService.getAdocFileOfSelectTitleTreeNode();
    }

    @Override
    public IncludeLine clickedIncludeLine() {
        return workViewService.getIncludeOfCursorLineOfSelectedPanel();
    }

    @Override
    public SyntaxAnalysisAO enterOperation() {
        return workViewService.getLineBeanOfCursorLineOfSelectedPanel();
    }
}
