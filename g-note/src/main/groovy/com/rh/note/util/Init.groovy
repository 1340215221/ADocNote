package com.rh.note.util


import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 控件
 */
abstract class Init<T> {

    private static final Logger log = LoggerFactory.getLogger(Init)
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
            log.error("Init.init error, 获取控件失败, componentId=[{}]", componentId)
            return
        }
    }

    /**
     * 获得控件
     */
     final T getBean() {
        return this.bean;
    }

}
