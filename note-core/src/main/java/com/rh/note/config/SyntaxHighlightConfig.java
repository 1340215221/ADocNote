package com.rh.note.config;

import com.rh.note.style.AdocFontStyle;
import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * 语法高亮配置
 */
@Getter
@Component
public class SyntaxHighlightConfig {
    /**
     * adoc样式
     */
    private AdocFontStyle adocFontStyle;
}
