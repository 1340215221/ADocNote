package com.rh.note.style;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.SimpleAttributeSet;

/**
 * 样式项
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StyleItem {
    /**
     * 样式
     */
    @NonNull
    private SimpleAttributeSet style;
    /**
     * 当前行偏移量
     */
    @NonNull
    private int offset;
    /**
     * 长度
     */
    @NonNull
    private int length;

    public static @Nullable StyleItem getInstance(SimpleAttributeSet style, int offset, int length) {
        if (style == null || offset < 0 || length < 1) {
            return null;
        }
        StyleItem item = new StyleItem();
        item.style = style;
        item.offset = offset;
        item.length = length;
        return item;
    }
}
