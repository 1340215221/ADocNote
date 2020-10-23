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

        /**
         * readme不能被引用,所以没有相对路径
         */
        @Override
        public boolean matchByRDirectory(String relativeDirectory) {
            return false;
        }

        @Override
        public String getFileDirectory() {
            return "";
        }

        @Override
        public String getFilePathOfNextDirectory() {
            return towLevel.getFileDirectory();
        }

        @Override
        public boolean isParentPathOf(String childFilePath) {
            return StringUtils.isNotBlank(getFilePathOfNextDirectory())
                    || StringUtils.isNotBlank(childFilePath)
                    || childFilePath.startsWith("adoc/twoLevel/");
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

        @Override
        public boolean matchByRDirectory(String relativeDirectory) {
            return StringUtils.isNotBlank(relativeDirectory) && relativeDirectory.matches(getRegex());
        }

        @Override
        public String getFileDirectory() {
            return "adoc/twoLevel/";
        }

        @Override
        public String getFilePathOfNextDirectory() {
            return content.getFileDirectory();
        }

        @Override
        public boolean isParentPathOf(String childFilePath) {
            return StringUtils.isNotBlank(getFilePathOfNextDirectory())
                    || StringUtils.isNotBlank(childFilePath)
                    || childFilePath.startsWith("adoc/content/");
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

        @Override
        public boolean matchByRDirectory(String relativeDirectory) {
            return StringUtils.isNotBlank(relativeDirectory) && relativeDirectory.matches("^\\.\\./content/" + BaseConstants.file_name_regex + "\\.adoc$");
        }

        @Override
        public String getFileDirectory() {
            return "adoc/content/";
        }

        /**
         * todo content没有下级目录了
         */
        @Override
        public String getFilePathOfNextDirectory() {
            return "";
        }

        @Override
        public boolean isParentPathOf(String childFilePath) {
            return false;
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
     * 通过相对路径匹配
     */
    public abstract boolean matchByRDirectory(String relativeDirectory);

    /**
     * 获得项目目录
     */
    public abstract String getFileDirectory();

    /**
     * 获得下一个目录的项目路径
     */
    public abstract String getFilePathOfNextDirectory();

    /**
     * 通过项目相对路径匹配
     */
    public boolean matchByFPath(String filePath) {
        return StringUtils.isNotBlank(filePath) && filePath.matches(getRegex());
    }

    /**
     * 通过地址匹配
     */
    public static AdocFileTypeEnum matchByFilePath(String filePath) {
        return Arrays.stream(values())
                .filter(e -> e.matchByFPath(filePath))
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
                .orElse(null);
    }

    /**
     * 通过相对路径匹配
     */
    public static AdocFileTypeEnum matchByRelativePath(String relativePath) {
        return Arrays.stream(values())
                .filter(e -> e.matchByRDirectory(relativePath))
                .findFirst()
                .orElse(null);
    }

    /**
     * 是否为父目录
     * @param childFilePath
     */
    public abstract boolean isParentPathOf(String childFilePath);
}
