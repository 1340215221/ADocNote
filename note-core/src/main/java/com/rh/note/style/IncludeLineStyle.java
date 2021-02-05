package com.rh.note.style;

import com.rh.note.syntax.IncludeSyntax;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

/**
 * include语句样式
 */
@RequiredArgsConstructor
public class IncludeLineStyle {

    /**
     * include::
     * []
     */
    private static final Color color = Color.decode("#00080");

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
    private @Nullable StyleItem getHeadStyle() {
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setForeground(set, color);
        int startOffset = syntax.getIndent().length();
        int endOffset = startOffset + 10;
        return StyleItem.getInstance(set, startOffset, endOffset);
    }

    /**
     * 引用范围样式
     * []
     */
    private @Nullable StyleItem getRangeStyle() {
        if (syntax.getLineNum1() != null || syntax.getLineNum2() != null) {
            return null;
        }
        SimpleAttributeSet set = new SimpleAttributeSet();
        // 缩进 include:: 路径
        int startOffset = syntax.getIndent().length() + 9 + syntax.getIncludePath().length();
        int endOffset = startOffset + 3;
        return StyleItem.getInstance(set, startOffset, endOffset);
    }

    /**
     * 引用范围样式
     * [lines=1]中的 [
     */
    private @Nullable StyleItem getRangeStylePre() {
        if (syntax.getLineNum1() == null && syntax.getLineNum2() == null) {
            return null;
        }
        SimpleAttributeSet set = new SimpleAttributeSet();
        // 缩进 include:: 路径
        int startOffset = syntax.getIndent().length() + 9 + syntax.getIncludePath().length();
        int endOffset = startOffset + 1;
        return StyleItem.getInstance(set, startOffset, endOffset);
    }

    /**
     * 引用范围样式
     * [lines=1]中的 ]
     */
    private @Nullable StyleItem getRangeStyleSub() {
        if (syntax.getLineNum1() == null && syntax.getLineNum2() == null) {
            return null;
        }
        SimpleAttributeSet set = new SimpleAttributeSet();
        int startOffset;
        // 缩进 include:: 路径 [lines=1..2
        if (syntax.getLineNum1() != null && syntax.getLineNum2() != null) {
            startOffset = syntax.getIndent().length() + 9 + syntax.getIncludePath().length() +;
        // 缩进 include:: 路径 [lines=1
        } else {
            startOffset = syntax.getIndent().length() + 9 + syntax.getIncludePath().length() +;
        }
        int startOffset = syntax.getIndent().length() + 9 + syntax.getIncludePath().length() + ;
        int endOffset = startOffset + 1;
        return StyleItem.getInstance(set, startOffset, endOffset);
    }

}
