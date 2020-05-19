package com.rh.note.action;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.api.IFileAPIService;
import com.rh.note.entity.adoc.impl.AdocProject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 编辑操作
 */
@RequiredArgsConstructor
public class EditAction {

    @NonNull
    private IFileAPIService fileAPIService;

    /**
     * 创建项目
     */
    public AdocProject createProject(CreateProjectAO ao) {
        return fileAPIService.createProject(ao);
    }

    /**
     * 创建标题
     */

    /**
     * 创建内容
     */

    /**
     * 编辑内容
     */

}
