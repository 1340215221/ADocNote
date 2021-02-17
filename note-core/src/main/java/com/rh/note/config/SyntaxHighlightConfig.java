package com.rh.note.config;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * 语法高亮配置
 */
@Component
public class SyntaxHighlightConfig {

    @Autowired
    private Font font;
    /**
     * 默认编辑区样式
     */
    @Getter
    @NonNull
    private SimpleAttributeSet defaultStyle;

    /**
     * 获得默认样式
     */
    @PostConstruct
    private void initDefaultStyle() {
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setFontSize(style, font.getSize());
        StyleConstants.setFontFamily(style, font.getFamily());
        defaultStyle = style;
    }
}
