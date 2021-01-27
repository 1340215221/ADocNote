package com.rh.note.common;

import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * 读取标题行
 */
public interface ReadTitleLineImpl extends IReadTitleLine {
    /**
     * 获得标题等级
     */
    Integer getLevel();

    /**
     * 获得父标题
     */
    ReadTitleLineImpl getParentTitle();

    /**
     * 获得子标题
     */
    <T extends IReadTitleLine> List<T> getChildrenTitle();

    /**
     * 设置父标题
     */
    void setParentTitle(ReadTitleLineImpl parent);

    /**
     * 查找父标题
     */
    default @Nullable ReadTitleLineImpl findParentOf(ReadTitleLineImpl titleLine) {
        if (titleLine == null) {
            return null;
        }
        if (getLevel() < titleLine.getLevel()) {
            return this;
        }
        if (getParentTitle() == null) {
            return null;
        }
        return getParentTitle().findParentOf(titleLine);
    }

    /**
     * 关联子标题
     */
    default void relationChild(ReadTitleLineImpl titleLine) {
        if (titleLine == null || getChildrenTitle() == null) {
            return;
        }
        getChildrenTitle().add(titleLine);
        titleLine.setParentTitle(this);
    }
}
