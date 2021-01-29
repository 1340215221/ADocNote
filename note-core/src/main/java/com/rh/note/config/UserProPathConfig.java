package com.rh.note.config;

import com.rh.note.vo.ProItemVO;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户的项目地址配置
 */
@Data
@Component
@PropertySource(value = {"classpath:config.yml"}, factory = PropertySourceFactory.class)
@ConfigurationProperties(prefix = "pro-path")
public class UserProPathConfig {
    /**
     * 项目地址
     */
    private List<ProItem> list;

    public @NotNull ProItemVO[] getProPathList() {
        if (CollectionUtils.isEmpty(list)) {
            return new ProItemVO[0];
        }
        return list.stream()
                .map(item -> {
                    ProItemVO vo = new ProItemVO();
                    vo.copy(item);
                    return vo;
                })
                .filter(ProItemVO::isNotEmpty)
                .toArray(ProItemVO[]::new);
    }

    /**
     * 项目项
     */
    @Data
    public static class ProItem {
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
