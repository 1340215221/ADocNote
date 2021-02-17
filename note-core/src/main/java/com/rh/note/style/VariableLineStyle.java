package com.rh.note.style;

import com.rh.note.common.ISyntaxStyleHandler;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 变量 行样式
 */
public class VariableLineStyle implements ISyntaxStyleHandler {
    /**
     * :java-path: 中 :
     */
    private static final Color varColor = Color.decode("#CC7832");
    /**
     * :java-path: maven/src/main/java 中 maven/src/main/java
     */
    private static final Color valueColor = Color.decode("#6A8759");
    /**
     * 变量正则
     */
    private static final String varRegex = "[^:{}\\s]+" + "|[^:{}\\s][^:{}]+[^:{}\\s]";
    /**
     * 值正则
     */
    private static final String valueRegex = "[^:\\s]+" + "|[^:\\s][^:]+[^:\\s]";
    /**
     * 正则
     */
    private static final String regex = "^(:)(?:" + varRegex + ")(:)\\s(" + valueRegex + ")\\s*$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public VariableLineStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getVarStylePre());
        list.add(getVarStyleSub());
        list.add(getValueStyle());
        return list;
    }

    /**
     * :java-path: 中 :
     */
    private @Nullable StyleItem getVarStylePre() {
        if (matcher == null || !isFind) {
            return null;
        }
        String var = matcher.group(1);
        if (StringUtils.isBlank(var)) {
            return null;
        }
        int startOffset = matcher.start(1);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, varColor);
        return StyleItem.getInstance(style, startOffset, var.length());
    }

    /**
     * :java-path: 中 :
     */
    private @Nullable StyleItem getVarStyleSub() {
        if (matcher == null || !isFind) {
            return null;
        }
        String var = matcher.group(2);
        if (StringUtils.isBlank(var)) {
            return null;
        }
        int startOffset = matcher.start(2);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, varColor);
        return StyleItem.getInstance(style, startOffset, var.length());
    }

    /**
     * :java-path: maven/src/main/java 中 maven/src/main/java
     */
    private @Nullable StyleItem getValueStyle() {
        if (matcher == null || !isFind) {
            return null;
        }
        String value = matcher.group(3);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        int startOffset = matcher.start(3);
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, valueColor);
        return StyleItem.getInstance(style, startOffset, value.length());
    }
}
