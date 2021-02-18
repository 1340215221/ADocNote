package com.rh.note.bean;

import com.rh.note.common.ISyntaxStyle;
import com.rh.note.constants.AdocSyntaxStyleEnum;
import com.rh.note.constants.JavaSyntaxStyleEnum;
import com.rh.note.constants.SyntaxTypeEnum;
import com.rh.note.style.StyleList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.text.SimpleAttributeSet;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * 语法样式上下文
 */
@RequiredArgsConstructor
public class SyntaxStyleContext {
    /**
     * 语法类型
     */
    @NonNull
    private SyntaxTypeEnum styleType;
    /**
     * 默认样式
     */
    @Getter
    @NonNull
    private SimpleAttributeSet defaultStyle;
    /**
     * 样式
     */
    private final StyleList styleList = new StyleList();

    /**
     * 读取一行内容
     */
    public void read(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return;
        }
        Stream<ISyntaxStyle> stream = null;
        if (SyntaxTypeEnum.ADOC.equals(styleType)) {
            stream = Arrays.stream(AdocSyntaxStyleEnum.values());
        }
        if (SyntaxTypeEnum.JAVA.equals(styleType)) {
            stream = Arrays.stream(JavaSyntaxStyleEnum.values());
        }
        if (stream == null) {
            return;
        }
        stream.filter((ISyntaxStyle e) -> { // 判断是否结束对当前行的解析
            StyleList list = e.match(lineContent, this);
            if (list == null || list.isEmpty()) {
                return false;
            }
            styleList.addAll(list);
            return e.isEnd();
        }).findFirst();
    }

    /**
     * 获得启用的样式
     */
    public @NotNull StyleList getEnableStyle() {
        return styleList.getEnableStyle();
    }
}
