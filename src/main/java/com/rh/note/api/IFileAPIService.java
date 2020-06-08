package com.rh.note.api;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.entity.adoc.AdocTitile;
import com.rh.note.entity.adoc.impl.AdocProject;
import lombok.NonNull;

import java.io.File;
import java.util.List;

/**
 * 文件操作api
 */
public interface IFileAPIService {

    /**
     * 创建项目
     */
    AdocProject createProject(@NonNull CreateProjectAO ao);

    File openAdocFile(String filePath);

    List<AdocTitile> queryProjectList(String s);
}
