package com.rh.note.constants;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * 文件路径处理
 */
public enum FilePathEnum {
    /**
     * readme
     */
    README {
        @Override
        public boolean matchByFilePath(String filePath) {
            return "README.adoc".equalsIgnoreCase(filePath);
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
    },
    /**
     * content
     */
    CONTENT {
        @Override
        public boolean matchByFilePath(String filePath) {
            return StringUtils.isNotBlank(filePath) && filePath.startsWith("adoc/content/");
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

    public abstract boolean matchByFilePath(String filePath);
}
