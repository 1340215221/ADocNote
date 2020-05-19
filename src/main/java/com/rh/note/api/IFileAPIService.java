package com.rh.note.api;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.entity.adoc.impl.AdocProject;
import lombok.NonNull;

/**
 * 文件操作api
 */
public interface IFileAPIService {

    /**
     * 创建项目
     */
    AdocProject createProject(@NonNull CreateProjectAO ao);
}
