package com.rh.note.service;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.entity.adoc.impl.AdocProject;
import lombok.NonNull;

/**
 * 文件服务
 * 将adoc对象实例化到文件
 * 或 从文件中解析出来adoc对象
 */
public interface IFileService {
    AdocProject createProject(@NonNull AdocProject adocProject);

    boolean checkProjectExist(@NonNull CreateProjectAO ao);

    AdocProject readProjectFile(@NonNull CreateProjectAO ao);
}
