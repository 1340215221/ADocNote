package com.rh.note.topic;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * 地摊接口
 */
public interface IStreetStall {

    /**
     * 获取起始点
     */
    Square.Cell getCell();

    /**
     * 数字标识
     */
    static Integer getNumber() {
        throw new RuntimeException();
    }

    /**
     * 获取节点2位置, 通过起始位置
     */
    Square.Cell getCell2();

    /**
     * 获取节点3位置, 通过起始位置
     */
    Square.Cell getCell3();

    /**
     * 获取节点4位置, 通过起始位置
     */
    Square.Cell getCell4();

    default boolean equals(Integer number) {
        return getNumber().equals(number);
    }

    default boolean occupy(Square.Cell cell) {
        if (this.getCell() == null) {
            return false;
        }
        return this.getCell().equals(cell)
                || getCell2().equals(cell)
                || getCell3().equals(cell)
                || getCell4().equals(cell);
    }

    default boolean continuous(Square.Cell cell) {
        if (this.getCell() == null) {
            return true;
        }
        return this.getCell().continuous(cell)
                || getCell2().continuous(cell)
                || getCell3().continuous(cell)
                || getCell4().continuous(cell);
    }

    /**
     * 添加该节点
     */
    void add();

    /**
     * 从space中移除,自己占用的节点
     */
    default void removeCellFrom(List<Square.Cell> space) {
        if (CollectionUtils.isEmpty(space) || this.getCell() == null) {
            return;
        }
        space.remove(this.getCell());
        space.remove(getCell2());
        space.remove(getCell3());
        space.remove(getCell4());
    }

    /**
     * 是否可以成功摆放
     */
    boolean isFails();
}
