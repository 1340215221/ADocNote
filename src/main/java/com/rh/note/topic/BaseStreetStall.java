package com.rh.note.topic;

import lombok.Getter;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 基础地摊类
 */
public abstract class BaseStreetStall implements IStreetStall {

    /**
     * 起始位置
     */
    @Getter
    protected Square.Cell cell;

    /**
     * 已添加节点计数器
     */
    private Integer okCount = 0;

    /**
     * 节点2
     */
    protected Square.Cell cell2;

    /**
     * 节点2
     */
    protected Square.Cell cell3;

    /**
     * 节点2
     */
    protected Square.Cell cell4;

    public void add() {
        okCount++;
    }

    public boolean isFails() {
        return okCount < 4;
    }

    private static Iterator<Integer> iterator = Stream.iterate(1, item -> item++).limit(100).collect(Collectors.toList()).iterator();

    /**
     * 获得地摊编号
     */
    protected static Integer getLabel() {
        iterator.hasNext();
        return iterator.next();
    }

    /**
     * 通过数字匹配元素
     * 根据匹配数量生成地摊
     */
    public static Stream<BaseStreetStall> match(List<Integer> numbers) {
        throw new RuntimeException();
    }
}
