package com.rh.note.bean;

import javax.swing.Icon;
import java.awt.Color;
import java.util.List;

/**
 * 标题语法
 * 视图需要使用到的方法
 */
public interface ITitleLine {
    /**
     * 获得标题名
     */
    String getTitleName();

    /**
     * 获得子标题
     */
    <T extends ITitleLine> List<ITitleLine> getChildrenTitles();

    /**
     * 获得文件路径
     */
    String getFilePath();

    /**
     * 获得节点图标
     */
    Icon getIcon();

    /**
     * 获得导航按钮颜色
     */
    Color getColor();
}
