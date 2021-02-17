package com.rh.note.style;

import com.rh.note.common.ISyntaxStyleHandler;
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
 * adoc标题引用 单词样式
 */
public class TitleQuoteWordStyle implements ISyntaxStyleHandler {
    /**
     * <<title>> 中 <<
     */
    private static final Color color = Color.decode("#287BDE");
    /**
     * 正则
     */
    private static final String regex = "(<<(?:" + RegexConstants.title_name_regex + ")>>)";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public TitleQuoteWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher.find()) {
            list.add(getMarkStyle());
        }
        return list;
    }

    /**
     * <<title>>
     */
    private @Nullable StyleItem getMarkStyle() {
        if (matcher == null) {
            return null;
        }
        String mark = matcher.group(1);
        if (StringUtils.isBlank(mark)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setUnderline(style, true);
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, mark.length());
    }

}
