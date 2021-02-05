package com.rh.note.config;

import com.rh.note.style.AdocFontStyle;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * 语法高亮配置
 */
@Component
public class SyntaxHighlightConfig {
    /**
     * adoc样式
     */
    @Getter
    private final AdocFontStyle adocFontStyle = new AdocFontStyle();
    @Autowired
    private Font font;

    /**
     * 获得默认样式
     */
    public @NotNull SimpleAttributeSet getDefaultStyle() {
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setFontSize(style, font.getSize());
        StyleConstants.setFontFamily(style, font.getFamily());
        return style;
    }
}
