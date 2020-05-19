package com.rh.note.service.impl;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.entity.adoc.impl.AdocProject;
import com.rh.note.service.IAdocService;
import com.rh.note.util.FileUtil;

public class AdocServiceImpl implements IAdocService {
    @Override
    public AdocProject createProject(CreateProjectAO ao) {
        // 创建目录
        String fullPath = ao.generateAbsolutePath();
        // 初始化主文件
        AdocProject adocProject = new AdocProject(fullPath, ao.getName());
        adocProject.initProjectInfo();
        return adocProject;
    }
}
