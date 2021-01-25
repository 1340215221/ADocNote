package com.rh.note.config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * adoc项目配置
 */
@Getter
@RequiredArgsConstructor
public class ProjectConfig {

    /**
     * 项目目录绝对路径
     */
    @NonNull
    private String proPath;

}
