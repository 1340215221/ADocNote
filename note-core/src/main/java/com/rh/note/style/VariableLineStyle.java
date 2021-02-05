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
 * 变量 行样式
 */
public class VariableLineStyle {
    /**
     * :java-path: 中 :
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 变量正则
     */
    private static final String varRegex = "[^:{}\\s]+" + "|[^:{}\\s][^:{}]+[^:{}\\s]";
    /**
     * 值正则
     */
    private static final String valueRegex = "[^:\\s]+" + "|[^:\\s][^:]+[^:\\s]";
    /**
     * 正则
     */
    private static final String regex = "^(:)(?:" + varRegex + ")(:)\\s(?:" + valueRegex + ")\\s*$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public VariableLineStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getMarkStylePre());
        list.add(getMarkStyleSub());
        return list;
    }

    /**
     * include::
     */
    private @Nullable StyleItem getMarkStylePre() {
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

    /**
     * include::
     */
    private @Nullable StyleItem getMarkStyleSub() {
        if (matcher == null || !isFind) {
            return null;
        }
        String mark = matcher.group(2);
        if (StringUtils.isBlank(mark)) {
            return null;
        }
        int startOffset = matcher.start(2);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, mark.length());
    }
}
