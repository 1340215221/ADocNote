package com.rh.note.constants;

import com.rh.note.style.BlockTitleLineStyle;
import com.rh.note.style.IncludeLineStyle;
import com.rh.note.style.LabelLineStyle;
import com.rh.note.style.StyleList;
import com.rh.note.style.TitleLineStyle;
import com.rh.note.style.VariableLineStyle;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

/**
 * adoc行语法 枚举
 */
public enum AdocLineSyntaxStyleEnum {
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
     * 标签
     */
    LABEL {
        @Override
        public @Nullable StyleList match(String lineContent) {
            StyleList style = new LabelLineStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    /**
     * 变量
     */
    VARIABLE {
        @Override
        public @Nullable StyleList match(String lineContent) {
            StyleList style = new VariableLineStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    /**
     * 块标题
     */
    BLOCK_TITLE {
        @Override
        public @Nullable StyleList match(@NonNull String lineContent) {
            StyleList style = new BlockTitleLineStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    }
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
