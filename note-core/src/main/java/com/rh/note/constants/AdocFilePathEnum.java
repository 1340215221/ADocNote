package com.rh.note.constants;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

/**
 * 文件路径处理
 */
public enum AdocFilePathEnum {
    /**
     * readme
     */
    README {
        @Override
        public boolean matchByFilePath(String filePath) {
            return "README.adoc".equalsIgnoreCase(filePath);
        }

        @Override
        public @NotNull String getNextLevelAdocDirectory() {
            return "adoc/twoLevel/";
        }
    },
    /**
     * twoLevel
     */
    TWO_LEVEL {
        @Override
        public boolean matchByFilePath(String filePath) {
            return StringUtils.isNotBlank(filePath) && filePath.startsWith("adoc/twoLevel/");
        }

        @Override
        public @NotNull String getNextLevelAdocDirectory() {
            return "adoc/content/";
        }
    },
    /**
     * content
     */
    CONTENT {
        @Override
        public boolean matchByFilePath(String filePath) {
            return StringUtils.isNotBlank(filePath) && filePath.startsWith("adoc/content/");
        }

        @Override
        public @Nullable String getNextLevelAdocDirectory() {
            return null;
        }
    },
    ;

    /**
     * include指向路径 转 项目路径<br/>
     * 只用于文件间的路径转换
     * 如果includePath为绝对路径, 则返回null
     */
    public static @Nullable String includePath2ProPath(String currentFileProPath, String includePath) {
        if (StringUtils.isBlank(currentFileProPath) || StringUtils.isBlank(includePath)
                || !currentFileProPath.matches(RegexConstants.file_path_regex)
                || !includePath.matches(RegexConstants.file_path_regex)
        ) {
            return null;
        }
        if (FileUtil.isAbsolutePath(includePath)) {
            return null;
        }
        // 获得当前目录
        String parentPath = Optional.of(currentFileProPath.lastIndexOf("/"))
                .map(index -> Math.max(index, 0))
                .map(index -> currentFileProPath.substring(0, index))
                .filter(StringUtils::isNotBlank)
                .map(path -> path + "/")
                .orElse("");
        return FileUtil.normalize(parentPath + includePath);
    }

    /**
     * 匹配, 通过文件项目路径
     */
    public static @Nullable AdocFilePathEnum findByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath) || isNotAdocFilePath(filePath)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(e -> e.matchByFilePath(filePath))
                .findFirst()
                .orElse(null);
    }

    /**
     * 匹配文件类型, 通过文件路径
     */
    public abstract boolean matchByFilePath(String filePath);

    /**
     * 获得当前文件类型的, 获得下一级别adoc文件目录
     * tip 返回路径以 / 结尾
     */
    public abstract @Nullable String getNextLevelAdocDirectory();

    /**
     * 判断不是adoc文件路径
     */
    public static boolean isNotAdocFilePath(String filePath) {
        return StringUtils.isBlank(filePath) || !filePath.endsWith(".adoc");
    }
}
