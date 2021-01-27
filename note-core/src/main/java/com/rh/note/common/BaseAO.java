package com.rh.note.common;

import com.rh.note.exception.RequestParamsValidException;

import java.io.Serializable;

/**
 * 基础参数
 */
public interface BaseAO extends Serializable {

    void checkRequiredItems() throws RequestParamsValidException;

}
