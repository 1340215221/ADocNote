package com.rh.note.app.config;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.awt.*;

/**
 * 用户yml配置文件配置
 */
@Configuration
public class UserConfigYmlConfig {
    /**
     * yml配置文件地址
     */
    private static final String configFilePath = "config.yml";

    /**
     * 应用配置
     */
    @Bean
    public UserAppConfig newUserAppConfig() {
        if (!FileUtil.isFile(configFilePath)) {
            return new UserAppConfig();
        }
        UserAppConfig config = new Yaml().loadAs(FileUtil.getReader(configFilePath, CharsetUtil.UTF_8), UserAppConfig.class);
        return config != null ? config : new UserAppConfig();
    }

    /**
     * 编辑区字体配置
     */
    @Bean
    public UserFontConfig newUserFontConfig(UserAppConfig appConfig) {
        return appConfig.getFont() != null ? appConfig.getFont() : new UserFontConfig();
    }

    /**
     * adoc项目配置
     */
    @Bean
    public UserProPathConfig newUserProPathConfig(UserAppConfig appConfig) {
        return appConfig.getProPath() != null ? appConfig.getProPath() : new UserProPathConfig();
    }

    /**
     * 编辑区字体
     */
    @Bean
    public Font newTextFont(UserFontConfig fontConfig) {
        OsInfo osInfo = SystemUtil.getOsInfo();
        if (osInfo.isLinux() && StringUtils.isNotBlank(fontConfig.getLinux())) {
            return new Font(fontConfig.getLinux(), Font.PLAIN, fontConfig.getSize());
        }
        if (osInfo.isWindows() && StringUtils.isNotBlank(fontConfig.getWin())) {
            return new Font(fontConfig.getWin(), Font.PLAIN, fontConfig.getSize());
        }
        return new Font(null, Font.PLAIN, fontConfig.getSize());
    }
}
