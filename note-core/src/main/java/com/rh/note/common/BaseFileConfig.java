package com.rh.note.common;

import com.rh.note.config.FrameLaunchConfig;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 基础文件配置
 */
public abstract class BaseFileConfig {

    /**
     * 项目配置对象
     */
    @Getter(value = AccessLevel.PRIVATE, lazy = true)
    private final FrameLaunchConfig config = initProConfig();

    /**
     * 从线程中获得项目配置
     */
    private @Nullable FrameLaunchConfig initProConfig() {
        ConfigurableApplicationContext app = ViewThreadContext.getThreadContextOrNull();
        if (app == null) {
            return null;
        }
        try {
            return app.getBean(FrameLaunchConfig.class);
        } catch (Exception e) { // 应该只拦截对象不存在异常
            return null;
        }
    }

    /**
     * 获得项目地址
     */
    protected @Nullable String getProPath() {
        FrameLaunchConfig frameLaunchConfig = getConfig();
        if (frameLaunchConfig == null) {
            return null;
        }
        return frameLaunchConfig.getProPath();
    }

}
