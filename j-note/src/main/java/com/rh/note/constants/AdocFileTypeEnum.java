package com.rh.note.constants;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * 文件类型
 */
public enum AdocFileTypeEnum {
    /**
     * readme
     */
    readme() {
        /**
         * README.adoc
         */
        @Override
        public String getRegex() {
            return "^README\\.adoc$";
        }

        @Override
        public String getRelativePathOfNextDirectory() {
            return "adoc/twoLevel/";
        }
    },
    /**
     * towLevel
     */
    towLevel() {
        /**
         * 例如
         * adoc/twoLevel/java基础.adoc
         */
        @Override
        public String getRegex() {
            return "^adoc/twoLevel/" + BaseConstants.file_name_regex + "\\.adoc$";
        }

        @Override
        public String getRelativePathOfNextDirectory() {
            return "../content/";
        }
    },
    /**
     * content
     */
    content() {
        /**
         * 例如
         * adoc/content/java基础.adoc
         */
        @Override
        public String getRegex() {
            return "^adoc/content/" + BaseConstants.file_name_regex + "\\.adoc$";
        }

        /**
         * content目录不支持引用adoc文件
         */
        @Override
        public String getRelativePathOfNextDirectory() {
            return "";
        }
    },
    ;

    /**
     * 通过项目路径匹配正则
     */
    public abstract String getRegex();

    /**
     * 获得下级目录的相对路径 todo
     */
    public abstract String getRelativePathOfNextDirectory();

    /**
     * 通过项目相对路径匹配
     */
    protected boolean matchByRegex(String filePath) {
        return StringUtils.isNotBlank(filePath) && filePath.matches(getRegex());
    }

    /**
     * 通过地址匹配
     */
    public static AdocFileTypeEnum matchByFilePath(String filePath) {
        return Arrays.stream(values())
                .filter(e -> e.matchByRegex(filePath))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获得实例
     */
    public static AdocFileTypeEnum getInstance(String name) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(name))
                .findFirst()
                .get();
    }

}
