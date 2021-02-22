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
 * 字符串 单词样式
 */
public class StringWordStyle implements ISyntaxStyleHandler {
    /**
     * db.user.query() 中的 query
     */
    private static final Color color = Color.decode("#6A8759");
    /**
     * 正则
     */
    private static final String regex = "(\".*?\"|'.*?')";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public StringWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher != null && matcher.find()) {
            list.add(getStringStyle());
        }
        return list;
    }

    private @Nullable StyleItem getStringStyle() {
        if (matcher == null) {
            return null;
        }
        String str = matcher.group(1);
        if (StringUtils.isBlank(str)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, str.length());
    }
}
