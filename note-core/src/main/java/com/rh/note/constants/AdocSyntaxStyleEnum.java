package com.rh.note.constants;

import cn.hutool.core.util.ReflectUtil;
import com.rh.note.bean.SyntaxStyleContext;
import com.rh.note.common.ISyntaxStyle;
import com.rh.note.common.ISyntaxStyleHandler;
import com.rh.note.style.BlockTitleLineStyle;
import com.rh.note.style.BoldWordStyle;
import com.rh.note.style.CodeWordStyle;
import com.rh.note.style.FootnoteWordStyle;
import com.rh.note.style.IncludeLineStyle;
import com.rh.note.style.InternetConnectionWordStyle;
import com.rh.note.style.ItalicWordStyle;
import com.rh.note.style.LabelLineStyle;
import com.rh.note.style.ListWordStyle;
import com.rh.note.style.PauseWordStyle;
import com.rh.note.style.StyleList;
import com.rh.note.style.TitleLineStyle;
import com.rh.note.style.TitleQuoteWordStyle;
import com.rh.note.style.VariableLineStyle;
import com.rh.note.util.LambdaUtil;
import lombok.NonNull;

import java.lang.reflect.Constructor;

/**
 * adoc语法样式
 */
public enum AdocSyntaxStyleEnum implements ISyntaxStyle {
    /**
     * include
     */
    INCLUDE(IncludeLineStyle.class),
    /**
     * 标题
     */
    TITLE (TitleLineStyle.class),
    /**
     * 标签
     */
    LABEL(LabelLineStyle.class),
    /**
     * 变量
     */
    VARIABLE(VariableLineStyle.class),
    /**
     * 块标题
     */
    BLOCK_TITLE(BlockTitleLineStyle.class),
    /**
     * 加粗
     */
    BOLD(BoldWordStyle.class),
    /**
     * 斜体
     */
    ITALIC(ItalicWordStyle.class),
    /**
     * 代码
     */
    CODE(CodeWordStyle.class),
    /**
     * 列表
     */
    LIST(ListWordStyle.class),
    /**
     * 标题引用
     */
    TITLE_QUOTE(TitleQuoteWordStyle.class),
    /**
     * 顿号
     */
    PAUSE(PauseWordStyle.class),
    /**
     * 脚注
     */
    FOOTNOTE(FootnoteWordStyle.class),
    /**
     * 网络链接
     */
    INTERNET_CONNECTION(InternetConnectionWordStyle.class),
    ;
    /**
     * 解析样式类
     */
    private final Constructor constructor;

    <T extends ISyntaxStyleHandler> AdocSyntaxStyleEnum(@NonNull Class<T> clazz) {
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
