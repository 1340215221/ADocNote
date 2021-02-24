package com.rh.note.constants;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * 语法类型
 */
public enum SyntaxTypeEnum {
    ADOC,
    JAVA,
    JS,
    XML,
    ;

    /**
     * 匹配语法类型, 通过文件名
     */
    public static @Nullable SyntaxTypeEnum matchByFilePath(String filePath) {
        String extName = FileUtil.extName(filePath);
        if (StringUtils.isBlank(extName)) {
            return null;
        }
        return Arrays.stream(values())
                .filter(e -> e.matchByExtName(extName))
                .findFirst()
                .orElse(null);
    }

    /**
     * 匹配语法类型, 通过文件拓展名
     */
    private boolean matchByExtName(String extName) {
        return this.name().equalsIgnoreCase(extName);
    };
}
