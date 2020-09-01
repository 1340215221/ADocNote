package com.rh.note.grammar;

import java.util.List;

/**
 * 标题语法对象接口
 */
public interface ITitleGrammar {

    /**
     * 获得标题名
     */
    String getName();
    /**
     * 获得子标题
     */
    List<? extends ITitleGrammar> getChildrenTitle();

}
