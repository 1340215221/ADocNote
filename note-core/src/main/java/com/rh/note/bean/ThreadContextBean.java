package com.rh.note.bean;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 线程容器操作对象
 */
@Getter
@RequiredArgsConstructor
public class ThreadContextBean {
    /**
     * 容器
     */
    @NonNull
    private ConfigurableApplicationContext context;
    /**
     * 进入切面层数
     */
    private int enterAspectLayer = 1;

    /**
     * 层数加一
     */
    public void increment() {
        enterAspectLayer++;
    }

    /**
     * 层数减一
     */
    public int decrement() {
        return --enterAspectLayer;
    }
}
