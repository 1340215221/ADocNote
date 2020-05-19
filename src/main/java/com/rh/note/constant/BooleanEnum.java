package com.rh.note.constant;

import lombok.Getter;

/**
 * 常用boolean枚举
 */
@Getter
public enum BooleanEnum {

    TRUE(0),
    FALSE(1),
    ;
    private Integer value;

    BooleanEnum(Integer value) {
        this.value = value;
    }

}
