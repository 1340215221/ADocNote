package com.rh.note.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;

/**
 * 视图容器工具类
 */
@NoArgsConstructor(access = AccessLevel.NONE)
public class ViewContextUtil {

    public static final ThreadLocal<ApplicationContext> context = new ThreadLocal<>();

}
