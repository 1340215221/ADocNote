package com.rh.note.util;

import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.NoteException;
import org.apache.commons.lang3.StringUtils;

/**
 * 枚举基础类
 */
public interface BaseEnum {

    default boolean equals(String t) {
        return name().equals(t);
    }

    static String name() {
        throw new NoteException(ErrorMessage.PARAMETER_ERROR);
    }

    static <T extends BaseEnum> T getInstance(String name) {
        if (StringUtils.isBlank(name)) {
            throw new NoteException(ErrorMessage.PARAMETER_ERROR);
        }
        return valueOf(name);
    }

    static <T extends BaseEnum> T valueOf(String name) {
        throw new NoteException(ErrorMessage.PARAMETER_ERROR);
    }
}
