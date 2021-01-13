package com.rh.note.constants;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 是否为单例
 */
@Getter
@RequiredArgsConstructor
public enum ScopeEnum {
    /**
     * 单例
     */
    SINGLETON(0),
    /**
     * 多例
     */
    ;
    @NonNull
    private Integer value;
}
