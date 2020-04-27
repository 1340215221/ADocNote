package com.rh.note.view.bound;

import java.util.Arrays;
import java.util.List;

/**
 * 各模块位置聚合类
 */
public class BoundFactory {

    /**
     * 获得主窗口位置
     */
    public static List<Integer> getMainFrameBound() {
        // todo 居中
        return Arrays.asList(200, 100, 900, 600);
    }

    /**
     * 编辑区域几行
     */
    public static Integer getEditAreaRow() {
        // 可调整占比
        return 34;
    }

    /**
     * 编辑区域几列
     */
    public static Integer getEditAreaColumn() {
        // 可调整占比
        return 50;
    }

    /**
     * 中间区域位置
     */
    public static List<Integer> getCenterAreaBound() {
        return Arrays.asList();
    }
}
