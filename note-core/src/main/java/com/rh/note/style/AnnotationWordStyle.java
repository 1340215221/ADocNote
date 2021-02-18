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
 * 注解 单词样式
 */
public class AnnotationWordStyle implements ISyntaxStyleHandler {
    /**
     * {@code @NotNull}
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 边界正则
     */
    private static final String border_regex = "\\s|@|[a-zA-Z]|\\(";
    /**
     * 正则
     */
    private static final String regex = "(?:" + border_regex + "|^)(@[a-zA-Z]+)(?=" + border_regex + "|$)";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public AnnotationWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher != null && matcher.find()) {
            list.add(getAnnotationStyle());
        }
        return list;
    }

    private @Nullable StyleItem getAnnotationStyle() {
        if (matcher == null) {
            return null;
        }
        String annotation = matcher.group(1);
        if (StringUtils.isBlank(annotation)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, annotation.length());
    }
}
