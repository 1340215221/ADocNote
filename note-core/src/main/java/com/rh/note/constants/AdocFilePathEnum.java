package com.rh.note.constants;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.rh.note.util.FilePathUtil;
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
        public @NotNull String getRelativePathOfNextLevelAdocDirectory() {
            return "adoc/twoLevel/";
        }
    },
    /**
     * twoLevel
     */
    TWO_LEVEL {
        @Override
        public boolean matchByFilePath(String filePath) {
            String regex = "^adoc/twoLevel/" + RegexConstants.file_name_regex + "\\.adoc$";
            return StringUtils.isNotBlank(filePath) && filePath.matches(regex);
        }

        @Override
        public @NotNull String getRelativePathOfNextLevelAdocDirectory() {
            return "../content/";
        }
    },
    /**
     * content
     */
    CONTENT {
        @Override
        public boolean matchByFilePath(String filePath) {
            String regex = "^adoc/content/" + RegexConstants.file_name_regex + "\\.adoc$";
            return StringUtils.isNotBlank(filePath) && filePath.matches(regex);
        }

        @Override
        public @Nullable String getRelativePathOfNextLevelAdocDirectory() {
            return null;
        }
    },
    ;

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
     * 判断项目路径是否在项目结构中
     */
    public static boolean isInProStructure(String filePath) {
        return findByFilePath(filePath) != null;
    }

    /**
     * 匹配文件类型, 通过文件路径
     */
    public abstract boolean matchByFilePath(String filePath);

    /**
     * 获得当前文件类型的, 获得下一级别adoc文件目录的相对路径
     * tip 返回路径以 / 结尾
     */
    public abstract @Nullable String getRelativePathOfNextLevelAdocDirectory();

    /**
     * 判断不是adoc文件路径
     */
    public static boolean isNotAdocFilePath(String filePath) {
        return StringUtils.isBlank(filePath) || !filePath.endsWith(".adoc");
    }
}
