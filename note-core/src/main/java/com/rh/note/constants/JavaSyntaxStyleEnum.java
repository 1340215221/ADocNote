package com.rh.note.constants;

import cn.hutool.core.util.ReflectUtil;
import com.rh.note.bean.SyntaxStyleContext;
import com.rh.note.common.ISyntaxStyle;
import com.rh.note.common.ISyntaxStyleHandler;
import com.rh.note.style.StyleList;
import com.rh.note.util.LambdaUtil;
import lombok.NonNull;

import java.lang.reflect.Constructor;

/**
 * java语法样式
 */
public enum JavaSyntaxStyleEnum implements ISyntaxStyle {
    ;
    /**
     * 解析样式类
     */
    private final Constructor constructor;

    <T extends ISyntaxStyleHandler> JavaSyntaxStyleEnum(@NonNull Class<T> clazz) {
        constructor = ReflectUtil.getConstructor(clazz, String.class, SyntaxStyleContext.class);
    }

    /**
     * 匹配样式
     */
    public StyleList match(String lineContent, SyntaxStyleContext context) {
        ISyntaxStyleHandler syntaxStyle = (ISyntaxStyleHandler) LambdaUtil.hiddenExceptionSup(() -> constructor.newInstance(lineContent, context));
        return syntaxStyle.getStyle();
    }
}
