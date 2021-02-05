package com.rh.note.style;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 标签 行样式
 */
public class LabelLineStyle {
    /**
     * [source,java] 中 [,]
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 正则
     */
    private static final String regex = "^(\\[)[^\\[\\],]*(,?)[^\\[\\],]*(\\])\\s*$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public LabelLineStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getBracketsStylePre());
        list.add(getCommaStyle());
        list.add(getBracketsStyleSub());
        return list;
    }

    /**
     * [source,java]
     * [
     */
    private @Nullable StyleItem getBracketsStylePre() {
        if (matcher == null || !isFind) {
            return null;
        }
        String brackets = matcher.group(1);
        if (StringUtils.isBlank(brackets)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, brackets.length());
    }

    /**
     * `code`
     * code
     */
    private @Nullable StyleItem getCommaStyle() {
        if (matcher == null || !isFind) {
            return null;
        }
        String comma = matcher.group(2);
        if (StringUtils.isBlank(comma)) {
            return null;
        }
        int startOffset = matcher.start(2);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, comma.length());
    }

    /**
     * `code`
     *      `
     */
    private @Nullable StyleItem getBracketsStyleSub() {
        if (matcher == null || !isFind) {
            return null;
        }
        String brackets = matcher.group(3);
        if (StringUtils.isBlank(brackets)) {
            return null;
        }
        int startOffset = matcher.start(3);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, brackets.length());
    }
}
