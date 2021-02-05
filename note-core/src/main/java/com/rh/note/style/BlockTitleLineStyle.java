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
 * 块标题 行样式
 */
public class BlockTitleLineStyle {
    /**
     * .标题
     */
    private static final Color color = Color.decode("#7a2518");
    /**
     * 正则
     */
    private static final String regex = "^(\\.(?:[^.\\s]+|[^.\\s].+[^\\s]))\\s*$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public BlockTitleLineStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getLineStyle());
        return list;
    }

    /**
     * include::
     */
    private @Nullable StyleItem getLineStyle() {
        if (matcher == null || !isFind) {
            return null;
        }
        String line = matcher.group(1);
        if (StringUtils.isBlank(line)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        StyleConstants.setItalic(style, true);
        return StyleItem.getInstance(style, startOffset, line.length());
    }
}
