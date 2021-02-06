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
 * adoc斜体 单词样式
 */
public class ItalicWordStyle {
    /**
     * _word_ 中 _
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 边界字符正则
     */
    private static final String boundaryRegex = "[^0-9a-zA-Z\\u4e00-\\u9fa5_]";
    /**
     * 代码内容正则
     */
    private static final String contentRegex = "[^_]+|[^_].+[^_]";
    /**
     * 正则
     */
    private static final String regex = "(?:^|" + boundaryRegex + ")(_)(" + contentRegex + ")(_)(?=$|" + boundaryRegex + ")";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public ItalicWordStyle(String lineContent) {
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
     * _word_
     * _
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
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, mark.length());
    }

    /**
     * _word_
     * word
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
        StyleConstants.setItalic(style, true);
        return StyleItem.getInstance(style, startOffset, head.length());
    }

    /**
     * _word_
     *      _
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
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, mark.length());
    }
}
