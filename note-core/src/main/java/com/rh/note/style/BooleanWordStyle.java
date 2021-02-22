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
 * 布尔类型 单词样式
 */
public class BooleanWordStyle implements ISyntaxStyleHandler {
    /**
     * true
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 正则
     */
    private static final String regex = "(?:^|\\s)(true|false|TRUE|FALSE)(?:$|\\s)";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public BooleanWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher != null && matcher.find()) {
            list.add(getBooleanStyle());
        }
        return list;
    }

    private @Nullable StyleItem getBooleanStyle() {
        if (matcher == null) {
            return null;
        }
        String b = matcher.group(1);
        if (StringUtils.isBlank(b)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, b.length());
    }
}
