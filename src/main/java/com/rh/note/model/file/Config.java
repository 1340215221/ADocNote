package com.rh.note.model.file;

import lombok.Data;

/**
 * 全局配置文件
 */
@Data
public class Config {

    /**
     * 项目路径变量
     */
    public static final String project_path = "{project-path}";
    /**
     * 项目路径
     */
    private String projectPath;
}
