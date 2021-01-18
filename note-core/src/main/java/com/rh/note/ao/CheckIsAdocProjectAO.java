package com.rh.note.ao;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 校验是adoc项目
 */
@Getter
@RequiredArgsConstructor
public class CheckIsAdocProjectAO {
    /**
     * 项目地址
     */
    @NonNull
    private String proPath;

    public ClickedProjectListAO copyTo() {
        return new ClickedProjectListAO().setProPath(proPath);
    }
}
