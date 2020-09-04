package com.rh.note.grammar;

import javax.swing.*;
import java.awt.*;
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
    /**
     * 获得颜色, 通过所在目录
     */
    Color getColor();

    String getFilePath();

    /**
     * 设置图标
     */
    Icon getIcon();
}
