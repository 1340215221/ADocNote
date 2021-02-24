package com.rh.note.constants;

import cn.hutool.core.util.ReflectUtil;
import com.rh.note.bean.SyntaxStyleContext;
import com.rh.note.common.ISyntaxStyle;
import com.rh.note.common.ISyntaxStyleHandler;
import com.rh.note.style.LabelCommentWordStyle;
import com.rh.note.style.LabelMarkWordStyle;
import com.rh.note.style.StringWordStyle;
import com.rh.note.style.StyleList;
import com.rh.note.util.LambdaUtil;
import lombok.NonNull;

import java.lang.reflect.Constructor;

/**
 * java语法样式
 */
public enum XmlSyntaxStyleEnum implements ISyntaxStyle {
    /**
     * 标签
     */
    LABEL(LabelMarkWordStyle.class),
    /**
     * 字符串
     */
    STRING(StringWordStyle.class),
    /**
     * 注释
     */
    COMMENT(LabelCommentWordStyle.class),
    ;
    /**
     * 解析样式类
     */
    private final Constructor constructor;

    <T extends ISyntaxStyleHandler> XmlSyntaxStyleEnum(@NonNull Class<T> clazz) {
        constructor = ReflectUtil.getConstructor(clazz, String.class);
//        constructor = ReflectUtil.getConstructor(clazz, String.class, SyntaxStyleContext.class);
    }

    /**
     * 匹配样式
     */
    public StyleList match(String lineContent, SyntaxStyleContext context) {
        ISyntaxStyleHandler syntaxStyle = (ISyntaxStyleHandler) LambdaUtil.hiddenExceptionSup(() -> constructor.newInstance(lineContent));
//        ISyntaxStyleHandler syntaxStyle = (ISyntaxStyleHandler) LambdaUtil.hiddenExceptionSup(() -> constructor.newInstance(lineContent, context));
        return syntaxStyle.getStyle();
    }
}
