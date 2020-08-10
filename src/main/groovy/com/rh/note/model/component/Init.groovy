package com.rh.note.model.component

import com.rh.note.util.ISwingBuilder
import org.apache.commons.lang3.StringUtils

import java.awt.*

/**
 * 控件
 */
abstract class Init<T> {

    private T bean

    /**
     * 查找一个swing控件,通过控件id
     */
    public <R extends Init> R init(String componentId) {
        if (StringUtils.isBlank(componentId)) {
            return
        }

        try {
            def bean = ISwingBuilder.swingBuilder."${componentId}"
            this.bean = bean
            return this
        } catch (Exception e) {
            System.err.println String.format("IComponent 获取控件失败=[{}]", componentId)
            return
        }
    }

    /**
     * 获得控件
     */
    protected final T getBean() {
        return this.bean;
    }

}
