package com.rh.note.style;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 代码 单词样式
 */
public class CodeWordStyle {
    /**
     * `code` 中 `
     */
    private static final Color markColor = Color.decode("#CC7832");
    /**
     * code 背景色
     */
    private static final Color codeColor = Color.decode("#3C3F41");
    /**
     * 边界字符正则
     */
    private static final String boundaryRegex = "[^0-9a-zA-Z\\u4e00-\\u9fa5`]*";
    /**
     * 代码内容正则
     */
    private static final String codeRegex = ".*[^`].*?";
    /**
     * 正则
     */
    private static final String regex = "(?:^|\\s)" + boundaryRegex + "(`)(" + codeRegex + ")(`)" + boundaryRegex + "(?=$|\\s)";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public CodeWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher.find()) {
            list.add(getMarkStylePre());
            list.add(getWordStyle());
            list.add(getMarkStyleSub());
        }
        return list;
    }

    /**
     * `code`
     * `
     */
    private @Nullable StyleItem getMarkStylePre() {
        if (matcher == null) {
            return null;
        }
        String mark = matcher.group(1);
        if (StringUtils.isBlank(mark)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, markColor);
        return StyleItem.getInstance(style, startOffset, mark.length());
    }

    /**
     * `code`
     * code
     */
    private @Nullable StyleItem getWordStyle() {
        if (matcher == null) {
            return null;
        }
        String head = matcher.group(2);
        if (StringUtils.isBlank(head)) {
            return null;
        }
        int startOffset = matcher.start(2);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setBackground(style, codeColor);
        return StyleItem.getInstance(style, startOffset, head.length());
    }

    /**
     * `code`
     *      `
     */
    private @Nullable StyleItem getMarkStyleSub() {
        if (matcher == null) {
            return null;
        }
        String mark = matcher.group(3);
        if (StringUtils.isBlank(mark)) {
            return null;
        }
        int startOffset = matcher.start(3);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, markColor);
        return StyleItem.getInstance(style, startOffset, mark.length());
    }
}
