package com.rh.note.app.config;

import lombok.Data;

/**
 * 用户字体配置
 */
@Data
public class UserFontConfig {
    /**
     * 行间距
     */
    private Float lineSpacing = 4f;
    /**
     * linux字体名
     */
    private String linux;
    /**
     * windows字体名
     */
    private String win;
    /**
     * 大小
     */
    private Integer size = 17;
}
