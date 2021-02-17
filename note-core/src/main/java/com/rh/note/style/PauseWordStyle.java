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
 * 自定义顿号 单词样式
 */
public class PauseWordStyle implements ISyntaxStyleHandler {
    /**
     * _玫瑰/百合_ 中 _/_
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 正则
     * 只匹配 '_'
     */
    private static final String regex = "(?:^|[^_])(_)((?:[^_/]+/)*[^_/]+)(_)(?=$|[^_])";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public PauseWordStyle(String lineContent) {
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
            list.addAll(getPauseStyle());
        }
        return list;
    }

    /**
     *  _玫瑰/百合_
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
     *  _玫瑰/百合_
     *   玫瑰
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
     *  _玫瑰/百合_
     *           _
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

    /**
     *  _玫瑰/百合_
     *       /
     */
    private @Nullable StyleList getPauseStyle() {
        if (matcher == null) {
            return null;
        }
        String content = matcher.group(2);
        if (StringUtils.isBlank(content)) {
            return null;
        }
        int startOffset = matcher.start(2);
        StyleList list = new StyleList();
        Matcher matcher1 = Pattern.compile("(?:[^_/]+(/))(?=[^_/]+)").matcher(content);
        while (matcher1.find()) {
            int start = matcher1.start(1);
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, color);
            StyleItem item = StyleItem.getInstance(style, startOffset + start, 1);
            list.add(item);
        }
        return list;
    }
}
