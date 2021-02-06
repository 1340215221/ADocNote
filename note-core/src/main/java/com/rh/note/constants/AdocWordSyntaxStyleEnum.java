package com.rh.note.constants;

import com.rh.note.style.*;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

/**
 * adoc单词语法 枚举
 */
public enum AdocWordSyntaxStyleEnum {
    /**
     * 加粗
     */
    BOLD {
        @Override
        public @Nullable StyleList match(String lineContent) {
            StyleList style = new BoldWordStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    /**
     * 斜体
     */
    ITALIC {
        @Override
        public @Nullable StyleList match(String lineContent) {
            StyleList style = new ItalicWordStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    /**
     * 代码
     */
    CODE {
        @Override
        public @Nullable StyleList match(String lineContent) {
            StyleList style = new CodeWordStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    /**
     * 列表
     */
    LIST {
        @Override
        public @Nullable StyleList match(String lineContent) {
            StyleList style = new ListWordStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    /**
     * 标题引用
     */
    TITLE_QUOTE {
        @Override
        public @Nullable StyleList match(String lineContent) {
            StyleList style = new TitleQuoteWordStyle(lineContent).getStyle();
            return style.isEmpty() ? null : style;
        }
    },
    ;

    public static StyleList generateStyle(String lineContent) {
        return Arrays.stream(values())
                .map(e -> e.match(lineContent))
                .filter(Objects::nonNull)
                .reduce(StyleList::addAll)
                .orElse(null);
    }

    public abstract @Nullable StyleList match(String lineContent);
}
