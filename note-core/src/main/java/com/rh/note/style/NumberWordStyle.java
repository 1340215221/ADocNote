package com.rh.note.style;

import com.rh.note.common.ISyntaxStyleHandler;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字 单词样式
 */
public class NumberWordStyle implements ISyntaxStyleHandler {
    /**
     * 13
     */
    private static final Color color = Color.decode("#6897BB");
    /**
     * 正则
     */
    private static final String regex = "(?:^|\\s)([0-9]+|[0-9]+\\.[0-9]+)(?:[^a-zA-Z0-9])";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public NumberWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher != null && matcher.find()) {
            list.add(getNumberStyle());
        }
        return list;
    }

    private @Nullable StyleItem getNumberStyle() {
        if (matcher == null) {
            return null;
        }
        String number = matcher.group(1);
        if (StringUtils.isBlank(number)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, number.length());
    }
}
