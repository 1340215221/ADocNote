package com.rh.note.action;

import com.rh.note.api.ProManageViewAPI;
import com.rh.note.bean.IAdocFile;
import lombok.Setter;

/**
 * 解析用户操作入口
 */
@Setter
public class OperationAction implements IOperationAction {

    private ProManageViewAPI proManageViewAPI;

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
    public boolean matchGenerateIncludeBlock(IAdocFile adocFile) {
        return false;
    }

    @Override
    public boolean matchGenerateTableBlock(IAdocFile adocFile) {
        return false;
    }

    @Override
    public String clickedHistoryProjectList() {
        return proManageViewAPI.getSelectedHistoryProjectPath();
    }
}
