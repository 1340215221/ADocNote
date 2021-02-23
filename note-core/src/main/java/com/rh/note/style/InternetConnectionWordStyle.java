package com.rh.note.style;

import com.rh.note.common.ISyntaxStyleHandler;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 网络链接 单词样式
 */
public class InternetConnectionWordStyle implements ISyntaxStyleHandler {
    /**
     * 标记颜色
     */
    private static final Color markColor = Color.decode("#CC7832");
    /**
     * 链接颜色
     */
    private static final Color connectionColor = Color.decode("#287BDE");
    /**
     * 正则
     */
    private static final String regex = "(http[s]?://.*?)(\\[).*?(\\])";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public InternetConnectionWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    @Override
    public StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher.find()) {
            list.add(getConnectionStyle());
            list.add(getMarkStylePre());
            list.add(getMarkStyleSub());
        }
        return list;
    }

    private StyleItem getConnectionStyle() {
        if (matcher == null) {
            return null;
        }
        String head = matcher.group(1);
        if (StringUtils.isBlank(head)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, connectionColor);
        return StyleItem.getInstance(style, startOffset, head.length());
    }

    private StyleItem getMarkStylePre() {
        if (matcher == null) {
            return null;
        }
        String mark = matcher.group(2);
        if (StringUtils.isBlank(mark)) {
            return null;
        }
        int startOffset = matcher.start(2);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, markColor);
        return StyleItem.getInstance(style, startOffset, mark.length());
    }

    private StyleItem getMarkStyleSub() {
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
