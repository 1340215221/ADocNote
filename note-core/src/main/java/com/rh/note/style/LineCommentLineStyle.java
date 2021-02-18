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
 * 行注释 行样式
 */
public class LineCommentLineStyle implements ISyntaxStyleHandler {
    /**
     * /**
     */
    private static final Color color = Color.decode("#808080");
    /**
     * 正则
     */
    private static final String regex = ".*?(//.*$)";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public LineCommentLineStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getLineStyle());
        return list;
    }

    private @Nullable StyleItem getLineStyle() {
        if (matcher == null || !isFind) {
            return null;
        }
        String comment = matcher.group(1);
        if (StringUtils.isBlank(comment)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, comment.length());
    }
}
