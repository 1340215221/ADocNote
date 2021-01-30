package com.rh.note.common;

import com.rh.note.exception.RequestParamsValidException;

import java.io.Serializable;

/**
 * 基础参数
 */
public interface BaseAO extends Serializable {
    /**
     * 检查请求参数错误
     */
    default boolean checkMissRequiredParams() { return false; }
    /**
     * 检查参数是否完整
     */
    default void assertThrow() throws RequestParamsValidException {}
}
