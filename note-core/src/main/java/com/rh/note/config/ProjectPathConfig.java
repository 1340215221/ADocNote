package com.rh.note.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目地址配置
 */
@Data
@Component
@ConfigurationProperties(prefix = "pro-path")
public class ProjectPathConfig {

    /**
     * 项目地址
     */
    private List<ProjectPathItem> list;

    @Data
    public static class ProjectPathItem {
        /**
         * 项目名
         */
        private String proName;
        /**
         * 项目地址
         */
        private String proPath;
    }

}
