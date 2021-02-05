package com.rh.note.style;

import com.rh.note.constants.AdocLineSyntaxStyleEnum;
import com.rh.note.constants.AdocWordSyntaxStyleEnum;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

/**
 * adoc字体样式
 */
public class AdocFontStyle {
    /**
     * 标题颜色
     */
    private Color titleColor = Color.decode("#e99b8f");
    /**
     * 块标题颜色
     */
    private Color blockTitleColor = Color.decode("#7a2518");
    /**
     * 1. include语句末尾中括号颜色
     * 2. 字体语法标记颜色, _ * `
     * 3. 注释序号颜色, <1>
     * 4. 标题列表语法标记颜色, - * .
     */
    private Color color1 = Color.decode("#00080");
    /**
     * 局部变量颜色, :java-path:
     */
    private Color color2 = Color.decode("#000FF");

    /**
     * 根据行内容, 生成样式
     */
    public @Nullable StyleList generateStyle(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        // 识别行语法
        StyleList lineStyles = AdocLineSyntaxStyleEnum.generateStyle(lineContent);
        if (lineStyles != null && !lineStyles.isEmpty()) {
            return lineStyles;
        }
        return AdocWordSyntaxStyleEnum.generateStyle(lineContent);
    }
}
