package com.rh.note.vo;

import javax.swing.Icon;
import java.util.List;

/**
 * 生成标题列表参数
 */
public interface ITitleLineVO {
    /**
     * 获得标题名
     */
    void getTitleName();

    /**
     * 获得子标题
     */
    List<ITitleLineVO> getChildrenTitles();

    /**
     * 得到对象地址
     */
    String getBeanPath();

    /**
     * 获得节点图标
     */
    Icon getTreeNodeIcon();
}
