package com.rh.note.file;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * java项目目录配置
 */
@Data
public class JavaProConfig {

    /**
     * 项目标识
     */
    @Data
    public static class ProLabel {
        /**
         * key是标识
         * value是项目路径
         */
        private Map<String, String> proLabel_proPath = new HashMap<>();

        {
            addPro("m", "maven"); // maven项目
            addPro("sm", "spring"); // spring项目
            addPro("sbm", "spring-boot"); // spring boot项目
            addPro("sbmw", "spring-boot-war"); // spring boot 的war包项目
        }

        /**
         * 添加
         */
        public void addPro(String proLabel, String proPath) {
            if (StringUtils.isBlank(proLabel) || StringUtils.isBlank(proPath)) {
                return;
            }

            proLabel_proPath.put(proLabel, proPath);
        }

        /**
         * 获得
         */
        public String getProPath(String proLabel) {
            if (StringUtils.isBlank(proLabel)) {
                return null;
            }

            return proLabel_proPath.get(proLabel);
        }
    }

}
