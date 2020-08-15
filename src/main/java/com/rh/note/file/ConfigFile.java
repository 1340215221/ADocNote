package com.rh.note.file;

import com.rh.note.common.IAdocFile;
import lombok.Data;

/**
 * 全局配置文件
 */
@Data
public class ConfigFile implements IAdocFile {

    /**
     * 项目路径变量
     */
    public static final String project_path = "{project-path}";
    /**
     * 项目路径
     */
    private String filePath;
}