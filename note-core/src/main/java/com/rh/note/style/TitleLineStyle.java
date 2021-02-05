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
 * 标题行样式
 */
public class TitleLineStyle {
    /**
     * === Java笔记
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 正则
     */
    private static final String regex = "^(=+)\\s+(" + RegexConstants.title_name_regex + ")\\s*$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public TitleLineStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getLevel());
        list.add(getTitleName());
        return list;
    }

    private StyleItem getTitleName() {
        if (matcher == null || !isFind) {
            return null;
        }
        String titleName = matcher.group(2);
        if (StringUtils.isBlank(titleName)) {
            return null;
        }
        int startOffset = matcher.start(2);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, titleName.length());
    }

    private @Nullable StyleItem getLevel() {
        if (matcher == null || !isFind) {
            return null;
        }
        String level = matcher.group(1);
        if (StringUtils.isBlank(level)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, level.length());
    }
}
