package com.rh.note.common;

/**
 * 遍历接口
 */
public interface IForEach<T, R> {

    void forEach(IHandler<T, R> handler);

    /**
     * 处理接口
     */
    interface IHandler<T, R> {
        R handle(T t);
    }

}
