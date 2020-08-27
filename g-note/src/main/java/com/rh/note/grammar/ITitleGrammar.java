package com.rh.note.grammar;

import java.util.List;

/**
 * 标题语法对象接口
 */
public interface ITitleGrammar {

    List<? extends ITitleGrammar> getChildrenTitle();

}
