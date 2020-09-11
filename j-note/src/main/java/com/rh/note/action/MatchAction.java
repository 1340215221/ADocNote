package com.rh.note.action;

import com.rh.note.bean.IAdocFile;

/**
 * 匹配操作入口
 */
public class MatchAction implements IMatchAction {
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
}
