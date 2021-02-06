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
 * 列表 单词样式
 * todo 应该是段落样式的
 */
public class ListWordStyle {
    /**
     * . - *
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 标记正则
     */
    private static final String markRegex = "[.\\-*]" + "|[.*]+";
    /**
     * 正则
     */
    private static final String regex = "^\\s*(" + markRegex + ")\\s+(?:[\\S]+|[\\S].+[\\S])\\s*$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public ListWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getMarkStyle());
        return list;
    }

    /**
     * include::
     */
    private @Nullable StyleItem getMarkStyle() {
        if (matcher == null || !isFind) {
            return null;
        }
        String mark = matcher.group(1);
        if (StringUtils.isBlank(mark)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, mark.length());
    }
}
