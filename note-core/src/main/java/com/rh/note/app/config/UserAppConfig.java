package com.rh.note.app.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户程序配置
 */
@Setter
@Getter
public class UserAppConfig {
    /**
     * adoc项目配置
     */
    private UserProPathConfig pro;
    /**
     * 编辑区字体配置
     */
    private UserFontConfig font;

}
