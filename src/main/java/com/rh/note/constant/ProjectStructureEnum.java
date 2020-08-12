package com.rh.note.constant;

import com.rh.note.exception.AdocException;
import com.rh.note.util.BaseEnum;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 项目结构
 */
@Getter
public enum ProjectStructureEnum implements BaseEnum {

    /**
     * read-me
     */
    read_me(){
        @Override
        public boolean match(String filePath) {
            if (StringUtils.isBlank(filePath)) {
                return false;
            }
            return filePath.endsWith("readme.adoc") || filePath.endsWith("README.adoc");
        }
    },
    /**
     * global-config
     */
    global_config() {
        @Override
        public boolean match(String filePath) {
            if (StringUtils.isBlank(filePath)) {
                return false;
            }
            return filePath.endsWith("config.adoc");
        }
    },
    /**
     * two-level
     */
    two_level() {
        @Override
        public boolean match(String filePath) {
            if (StringUtils.isBlank(filePath)) {
                return false;
            }
            filePath.contains("twoLevel")
        }
    },
    /**
     * content
     */
    content(),
    ;

    /**
     * 配置实例，通过文件地址
     */
    public abstract boolean match(String filePath);

    /**
     * 获得实例
     */
    public static ProjectStructureEnum getInstance(String name) {
        return BaseEnum.getInstance(name);
    }

    /**
     * 匹配实例，通过文件地址
     */
    public static ProjectStructureEnum matchInstance(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            throw new AdocException(ErrorMessage.PARAMETER_ERROR);
        }
        return Arrays.stream(values())
                .filter(e -> e.match(filePath))
                .findFirst()
                .orElseThrow(() -> new AdocException(ErrorMessage.PARAMETER_ERROR));
    }
}
