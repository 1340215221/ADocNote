package com.rh.note.constants;

import com.rh.note.common.BaseEnum;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 是否为单例
 */
@Getter
@RequiredArgsConstructor
public enum ScopeEnum implements BaseEnum<String> {
    /**
     * 单例
     */
    SINGLETON("singleton"),
    /**
     * 多例
     */
    PROTOTYPE("prototype"),
    ;
    @NonNull
    private String value;
}
