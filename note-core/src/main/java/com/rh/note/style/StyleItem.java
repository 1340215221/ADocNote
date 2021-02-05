package com.rh.note.style;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private SimpleAttributeSet style;
    /**
     * 起始索引
     */
    private int startOffset;
    /**
     * 结束索引
     */
    private int endOffset;

    public static @Nullable StyleItem getInstance(SimpleAttributeSet style, int startOffset, int endOffset) {
        if (style == null || startOffset < 0 || endOffset <= startOffset) {
            return null;
        }
        StyleItem item = new StyleItem();
        item.style = style;
        item.startOffset = startOffset;
        item.endOffset = endOffset;
        return item;
    }
}
