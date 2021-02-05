package com.rh.note.constants;

import com.rh.note.style.IncludeLineStyle;
import com.rh.note.style.StyleList;
import com.rh.note.style.TitleLineStyle;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

/**
 * adoc行语法 枚举
 */
public enum AdocLineSyntaxEnum {
    /**
     * include
     */
    INCLUDE {
        @Override
        public @Nullable StyleList match(@NonNull String lineContent) {
            StyleList style = new IncludeLineStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    /**
     * 标题
     */
    TITLE {
        @Override
        public @Nullable StyleList match(@NonNull String lineContent) {
            StyleList style = new TitleLineStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    /**
     * 块标题
     */
    /**
     * 代码块
     */
    ;

    /**
     * 生成样式
     */
    public static @Nullable StyleList generateStyle(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        return Arrays.stream(values())
                .map(e -> e.match(lineContent))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    /**
     * 匹配行内容, 生成样式
     */
    public abstract @Nullable StyleList match(@NonNull String lineContent);
}
