package com.rh.note.style;

import com.rh.note.common.ISyntaxStyleHandler;
import org.apache.commons.lang3.StringUtils;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 待完成 单词样式
 */
public class TodoWordStyle implements ISyntaxStyleHandler {
    /**
     * 标记颜色
     */
    private static final Color markColor = Color.decode("#808080");
    /**
     * 内容颜色
     */
    private static final Color contentColor = Color.decode("#A8C023");
    /**
     * 正则
     */
    private static final String regex = ".*?(//)\\s*((?:todo|TODO)(?:\\s+.*)?)\\s*$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 查找一次
     */
    private boolean isFind;

    public TodoWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    @Override
    public StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getMarkStyle());
        list.add(getContentPre());
        return list;
    }

    private StyleItem getMarkStyle() {
        if (matcher == null || !isFind) {
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

    private StyleItem getContentPre() {
        if (matcher == null || !isFind) {
            return null;
        }
        String content = matcher.group(2);
        if (StringUtils.isBlank(content)) {
            return null;
        }
        int startOffset = matcher.start(2);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, contentColor);
        StyleConstants.setItalic(style, true);
        return StyleItem.getInstance(style, startOffset, content.length());
    }
}
