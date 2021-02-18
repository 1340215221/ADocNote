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
 * 关键字 单词样式
 */
public class KeywordWordStyle implements ISyntaxStyleHandler {
    /**
     * package import public
     */
    private static final Color color = Color.decode("#CC7832");
    /**
     * 关键字列表
     */
    private static final String keyword_regex = "package|import|public|private|static|final|implements|extends"
            + "|protected|int|boolean|float|double|char|return|null|continue|break|for|if|while|new|throw|throws"
            + "|class|interface|enum|default|abstract";
    /**
     * 边界正则
     */
    private static final String border_regex = "\\s|;|\\(|\\)|\\.|=|,|@";
    /**
     * 正则
     */
    private static final String regex = "(?:" + border_regex + "|^)(" + keyword_regex + ")(?=" + border_regex + "|$)";
    /**
     * 匹配器
     */
    private Matcher matcher;

    public KeywordWordStyle(String lineContent) {
        if (StringUtils.isNotBlank(lineContent)) {
            matcher = Pattern.compile(regex).matcher(lineContent);
        }
    }

    public @NotNull StyleList getStyle() {
        StyleList list = new StyleList();
        while (matcher != null && matcher.find()) {
            list.add(getWordStyle());
        }
        return list;
    }

    private @Nullable StyleItem getWordStyle() {
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
