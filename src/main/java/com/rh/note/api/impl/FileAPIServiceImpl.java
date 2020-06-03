package com.rh.note.api.impl;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.api.IFileAPIService;
import com.rh.note.entity.adoc.impl.AdocProject;
import com.rh.note.service.IAdocService;
import com.rh.note.service.IFileService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 文件操作api
 */
@RequiredArgsConstructor
public class FileAPIServiceImpl implements IFileAPIService {

    @NonNull
    private IFileService fileService;
    @NonNull
    private IAdocService adocService;

    /**
     * 创建项目
     */
    @Override
    public AdocProject createProject(@NonNull CreateProjectAO ao) {
        //判断项目是否存在
        if (fileService.checkProjectExist(ao)) {
            //存在, 解析项目
            return fileService.readProjectFile(ao);
        }

        //不存在, 初始化项目
        AdocProject adocProject = adocService.createProject(ao);
        fileService.createProject(adocProject);
        return adocProject;
    }
}
