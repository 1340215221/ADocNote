package com.rh.note.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * 窗口启动配置
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FrameLaunchConfig {

    /**
     * 项目目录绝对路径
     */
    @NonNull
    private String proPath;

    public static @Nullable FrameLaunchConfig getInstance(String proPath) {
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        FrameLaunchConfig config = new FrameLaunchConfig();
        config.proPath = proPath;
        return config;
    }

    public String getProPath() {
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        return proPath.endsWith("/") ? proPath : proPath + "/";
    }

}
