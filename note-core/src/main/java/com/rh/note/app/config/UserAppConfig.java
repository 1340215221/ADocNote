package com.rh.note.app.config;

import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.awt.Font;

/**
 * 用户程序配置
 */
@Configuration
@PropertySource(value = {"classpath:config.yml"}, factory = PropertySourceFactory.class)
public class UserAppConfig {

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
        return new Font(null, Font.PLAIN, 17);
    }

    /**
     * 字体配置
     */
    @Bean
    @ConfigurationProperties(prefix = "font")
    public UserFontConfig newUserFontConfig() {
        return new UserFontConfig();
    }

    /**
     * 项目路径配置
     */
    @Bean
    @ConfigurationProperties(prefix = "pro-path")
    public UserProPathConfig newUserProPathConfig() {
        return new UserProPathConfig();
    }

}
