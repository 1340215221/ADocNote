package com.rh.note.topic;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        final List<Integer> numbers = Arrays.asList(7, 4, 3, 3, 0, 2, 1, 1, 1, 0, 4, 1, 2, 2, 1, 1, 1, 3, 2, 2);
        // 数字转化为地摊
        // 生成地摊列表
        List<BaseStreetStall> streetStalls = StreetStallEnum.generateList(numbers);
        // 依次放入广场 todo 之后实现打乱地摊顺序再次放入
        Square square = new Square();
        streetStalls.forEach(square::add);
        // 直到广场不能放下
        square.printSpace();
    }

}
