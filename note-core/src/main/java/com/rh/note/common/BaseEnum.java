package com.rh.note.common;

import java.io.Serializable;

/**
 * 基础枚举类
 */
public interface BaseEnum<T extends Serializable> {
    T getValue();
    default boolean equals(T value){
        return getValue().equals(value);
    }
}
