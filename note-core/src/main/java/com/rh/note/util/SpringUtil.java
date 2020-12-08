package com.rh.note.util;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * spring工具类
 */
@Component
public class SpringUtil {

    private static ApplicationContext app;

    public SpringUtil(ApplicationContext app) {
        SpringUtil.app = app;
    }

    public static <T> T get(Class<T> clazz) {
        return app.getBean(clazz);
    }

}
