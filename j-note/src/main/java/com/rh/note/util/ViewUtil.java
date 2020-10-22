package com.rh.note.util;

import com.rh.note.base.ISwingBuilder;
import groovy.swing.SwingBuilder;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

/**
 * swing工具类
 */
public final class ViewUtil {

    private static final SwingBuilder swingBuilder = ISwingBuilder.swingBuilder;

    /**
     * 删除swingBuilder中的控件
     */
    public static void removeByComponentId(String componentId) {
        if (StringUtils.isBlank(componentId)) {
            return;
        }

        Map variables = swingBuilder.getVariables();
        if (MapUtils.isEmpty(variables)) {
            return;
        }

        variables.remove(componentId);
    }

    /**
     * 获得某类型的控件
     */
    public static <T> @NotNull Stream<T> getComponentsByClass(Class<T> clazz) {
        if (clazz == null) {
            return Stream.empty();
        }

        Map<Object, Object> variables = swingBuilder.getVariables();
        if (MapUtils.isEmpty(variables)) {
            return Stream.empty();
        }

        return variables.entrySet().stream()
                .filter(entry -> entry.getKey() instanceof String && entry.getValue() != null && clazz.isInstance(entry.getValue()))
                .map(entry -> ((T) entry.getValue()));
    }

}
