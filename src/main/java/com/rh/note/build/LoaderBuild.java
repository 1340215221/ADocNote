package com.rh.note.build;

import com.rh.note.load.ProjectListLoader;
import com.rh.note.load.WorkLoader;

/**
 * 构建加载器
 */
public interface LoaderBuild {
    /**
     * 项目列表数据加载器
     */
    ProjectListLoader projectListLoader = new ProjectListLoader();
    WorkLoader workLoader = new WorkLoader();
}
