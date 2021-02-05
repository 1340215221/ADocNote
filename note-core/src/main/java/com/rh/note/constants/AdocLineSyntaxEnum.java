package com.rh.note.constants;

import com.rh.note.style.IncludeLineStyle;
import com.rh.note.style.StyleList;
import com.rh.note.syntax.IncludeSyntax;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

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
            IncludeSyntax syntax = new IncludeSyntax().init(lineContent);
            if (syntax == null) {
                return null;
            }
            IncludeLineStyle style = new IncludeLineStyle(syntax);
            return style.getStyle();
        }
    },
    /**
     * 标题
     */
    TITLE,
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
                .findFirst()
                .orElse(null);
    }

    /**
     * 匹配行内容, 生成样式
     */
    public abstract @Nullable StyleList match(@NonNull String lineContent);
}
