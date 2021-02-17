package com.rh.note.common;

import com.rh.note.bean.SyntaxStyleContext;
import com.rh.note.style.StyleList;

/**
 * 语法样式
 */
public interface ISyntaxStyle {
    StyleList match(String lineContent, SyntaxStyleContext context);
    /**
     * 是否结束当前行样式判断
     */
    default boolean isEnd() {
        return false;
    }
}
