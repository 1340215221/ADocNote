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
    README,
    /**
     * twoLevel
     */
    TWO_LEVEL,
    /**
     * content
     */
    CONTENT,
    ;

    /**
     * 相对路径 转 项目路径<br/>
     * 只用于文件间的路径转换
     */
    public static @Nullable String coverProPath(String currentFileProPath, String relativePath) {
        if (StringUtils.isBlank(currentFileProPath) || StringUtils.isBlank(relativePath)
                || !currentFileProPath.matches(RegexConstants.file_path_regex)
                || !relativePath.matches(RegexConstants.file_path_regex)
        ) {
            return null;
        }
        // 获得当前目录
        String parentPath = Optional.of(currentFileProPath.lastIndexOf("/"))
                .map(index -> Math.max(index, 0))
                .map(index -> currentFileProPath.substring(0, index))
                .filter(StringUtils::isNotBlank)
                .map(path -> path + "/")
                .orElse("");
        return FileUtil.normalize(parentPath + relativePath);
    }

}
