package com.rh.note.util;

import com.rh.note.common.ViewThreadContext;
import com.rh.note.config.FrameLaunchConfig;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 当前adoc项目配置 工具
 */
public class CurrentAdocProConfigUtil {

    /**
     * 获得项目绝对路径
     * tip 返回目录以 / 结尾
     */
    public static @Nullable String getProPath() {
        ConfigurableApplicationContext app = ViewThreadContext.getThreadContextOrNull();
        if (app == null) {
            return null;
        }
        try {
            FrameLaunchConfig config = app.getBean(FrameLaunchConfig.class);
            return config.getProPath();
        } catch (NoSuchBeanDefinitionException e) { // 应该只拦截对象不存在异常
            return null;
        }
    }
}
