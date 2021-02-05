package com.rh.note.style;

import com.rh.note.constants.RegexConstants;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * include语句样式
 */
public class IncludeLineStyle {
    /**
     * include::
     * []
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 正则
     */
    private static final String regex = "^\\s*(include::)(?:" + RegexConstants.file_path_regex + ")(\\[)(?:lines=[0-9]+(?:..[0-9]+)?)?(\\])$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public IncludeLineStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getHeadStyle());
        list.add(getRangePre());
        list.add(getRangeSub());
        return list;
    }

    /**
     * include::
     */
    private @Nullable StyleItem getHeadStyle() {
        if (matcher == null || !isFind) {
            return null;
        }
        String head = matcher.group(1);
        if (StringUtils.isBlank(head)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, head.length());
    }

    /**
     * [lines=1..2]
     * [
     */
    private @Nullable StyleItem getRangePre() {
        if (matcher == null || !isFind) {
            return null;
        }
        String rangePre = matcher.group(2);
        if (StringUtils.isBlank(rangePre)) {
            return null;
        }
        int startOffset = matcher.start(2);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, rangePre.length());
    }

    /**
     * [lines=1..2]
     * ]
     */
    private @Nullable StyleItem getRangeSub() {
        if (matcher == null || !isFind) {
            return null;
        }
        String rangeSub = matcher.group(3);
        if (StringUtils.isBlank(rangeSub)) {
            return null;
        }
        int startOffset = matcher.start(3);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, rangeSub.length());
    }
}
