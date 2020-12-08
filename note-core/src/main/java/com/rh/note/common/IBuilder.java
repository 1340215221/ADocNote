package com.rh.note.common;

import groovy.lang.Closure;

public interface IBuilder {
    void init(Closure children);

    default void destroy() {}
}
