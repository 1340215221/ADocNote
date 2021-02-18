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
 * 方法名 行样式
 */
public class MethodNameLineStyle implements ISyntaxStyleHandler {
    /**
     */
    private static final Color color = Color.decode("#FFC66D");
    /**
     * 参数项正则
     */
    private static final String param_item_regex = "\\s*(?:[a-zA-Z<>\\[\\]]+\\s+)?[a-zA-Z]+\\s*";
    /**
     * 泛型项正则
     */
    private static final String generic_item_regex = "\\s*[A-Z]+(?:\\s+extends\\s+[a-zA-Z<>\\[\\]]+)\\s*";
    /**
     * 正则
     */
    public static final String regex = "^\\s*(?:(?:public|private|protected)\\s+)?" // 访问符
            + "(?:(?:static|default)\\s+)?" // 静态修饰符
            + "(?:final\\s+)?"
            + "(?:<(?:" + generic_item_regex + ",)*(?:" + generic_item_regex + ")?>\\s+)?" // 泛型
            + "(?:@[a-zA-Z]+\\s+)?" // 注解
            + "(?:(?:[a-zA-Z<>\\[\\]]+|[a-zA-Z][a-zA-Z0-9<>\\[\\]]+)\\s+)?" // 返回值
            + "([a-z]|[a-z][a-zA-Z0-9]+)\\s*" // 方法名
            + "\\((?:" + param_item_regex + ",)*(?:" + param_item_regex + ")?\\)\\s*" // 参数
            + "\\{(?:\\s*\\})?\\s*$";
    /**
     * 匹配器
     */
    private Matcher matcher;
    /**
     * 是否查找到
     */
    private boolean isFind;

    public MethodNameLineStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
            isFind = matcher.find();
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        list.add(getMethodNameStyle());
        return list;
    }

    private @Nullable StyleItem getMethodNameStyle() {
        if (matcher == null || !isFind) {
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
