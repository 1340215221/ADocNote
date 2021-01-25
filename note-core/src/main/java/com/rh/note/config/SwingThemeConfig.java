package com.rh.note.config;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 主题配置
 */
@Component
public class SwingThemeConfig {

    @PostConstruct
    public void init() {
        FlatDarculaLaf.install();
    }

}
