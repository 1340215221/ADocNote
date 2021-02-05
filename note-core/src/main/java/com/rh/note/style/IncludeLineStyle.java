package com.rh.note.style;

import com.rh.note.syntax.IncludeSyntax;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * include语句样式
 */
@RequiredArgsConstructor
public class IncludeLineStyle {

    @NonNull
    private IncludeSyntax syntax;

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getHeadStyle());
        list.add(getRangeStyle());
        list.add(getRangeStylePre());
        list.add(getRangeStyleSub());
        return list;
    }

    /**
     * 引用头样式
     * include::
     */
    private @NotNull StyleItem getHeadStyle() {
    }

    /**
     * 引用范围样式
     * []
     */
    private @Nullable StyleItem getRangeStyle() {
    }

    /**
     * 引用范围样式
     * [lines=1]中的 [
     */
    private @Nullable StyleItem getRangeStylePre() {
    }

    /**
     * 引用范围样式
     * [lines=1]中的 ]
     */
    private @Nullable StyleItem getRangeStyleSub() {
    }

}
