package com.rh.note.constants;

import cn.hutool.core.util.ReflectUtil;
import com.rh.note.bean.SyntaxStyleContext;
import com.rh.note.common.ISyntaxStyle;
import com.rh.note.common.ISyntaxStyleHandler;
import com.rh.note.style.*;
import com.rh.note.util.LambdaUtil;
import lombok.NonNull;

import java.lang.reflect.Constructor;

/**
 * mongodb语法样式
 */
public enum JsSyntaxStyleEnum implements ISyntaxStyle {
    /**
     * 变量名
     */
    VAR_NAME(VarNameWordStyle.class),
    /**
     * 方法名
     */
    METHOD_NAME(MethodNameWordStyle.class),
    /**
     * 字符串
     */
    STRING(StringWordStyle.class),
    /**
     * 数字
     */
    NUMBER(NumberWordStyle.class),
    /**
     * 布尔
     */
    BOOLEAN(BooleanWordStyle.class),
    /**
     * 行注释
     */
    LINE_COMMENT(LineCommentLineStyle.class),
    /**
     * 标点
     */
    PUNCTUATION(PunctuationWordStyle.class),
    ;
    /**
     * 解析样式类
     */
    private final Constructor constructor;

    <T extends ISyntaxStyleHandler> JsSyntaxStyleEnum(@NonNull Class<T> clazz) {
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
