package com.rh.note.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 窗口启动配置
 */
@RequiredArgsConstructor
public class FrameLaunchConfig {

    /**
     * 项目目录绝对路径
     */
    @NonNull
    private String proPath;

    public String getProPath() {
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        return proPath.endsWith("/") ? proPath : proPath + "/";
    }

}
