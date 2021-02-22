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
 * 变量名 单词样式
 */
public class VarNameWordStyle implements ISyntaxStyleHandler {
    /**
     * name: '11' 中 name
     */
    private static final Color color = Color.decode("#9876AA");
    /**
     * 正则
     */
    private static final String regex = "([_]*[a-zA-Z][a-zA-Z0-9_]*)\\s*:";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public VarNameWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher != null && matcher.find()) {
            list.add(getVarNameStyle());
        }
        return list;
    }

    private @Nullable StyleItem getVarNameStyle() {
        if (matcher == null) {
            return null;
        }
        String varName = matcher.group(1);
        if (StringUtils.isBlank(varName)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);
        return StyleItem.getInstance(style, startOffset, varName.length());
    }
}
