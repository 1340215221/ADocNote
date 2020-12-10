package com.rh.note.common;

import javax.annotation.PreDestroy;

interface ISingletonBuilder {
    /**
     * 销毁方法
     */
    @PreDestroy
    void destroy();
}
