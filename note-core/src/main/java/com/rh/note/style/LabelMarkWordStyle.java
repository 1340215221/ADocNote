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
 * 标签符号 单词样式
 */
public class LabelMarkWordStyle implements ISyntaxStyleHandler {
    /**
     * <div></div><div/>
     */
    private static final Color color = Color.decode("#E8BF6A");
    /**
     * 标签名正则
     */
    private static final String label_name_regex = "[a-zA-Z][a-zA-Z0-9.]*";
    /**
     * 正则
     */
    private static final String regex = "(<" + label_name_regex + ">?|<?/" + label_name_regex + ">|<" + label_name_regex + "/>|<\\?|\\?>)";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public LabelMarkWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher != null && matcher.find()) {
            list.add(getMarkPre());
        }
        return list;
    }

    private @Nullable StyleItem getMarkPre() {
        if (matcher == null) {
            return null;
        }
        String word = matcher.group(1);
        if (StringUtils.isBlank(word)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, word.length());
    }
}
