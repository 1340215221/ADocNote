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
 * 方法名 单词样式
 */
public class MethodNameWordStyle implements ISyntaxStyleHandler {
    /**
     * db.user.query() 中的 query
     */
    private static final Color color = Color.decode("#FFC66D");
    /**
     * 正则
     */
    private static final String regex = "\\.([0-9a-zA-Z]+)\\(";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public MethodNameWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher != null && matcher.find()) {
            list.add(getMethodNameStyle());
        }
        return list;
    }

    private @Nullable StyleItem getMethodNameStyle() {
        if (matcher == null) {
            return null;
        }
        String methodName = matcher.group(1);
        if (StringUtils.isBlank(methodName)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, methodName.length());
    }
}
