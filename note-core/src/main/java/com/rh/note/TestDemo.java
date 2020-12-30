package com.rh.note;

import java.util.Arrays;

/**
 * 空的main方法, 用于编译项目
 */
public class TestDemo {

    public static void main(String[] args) {
        String[] arr = {"appId", "timeStamp", "nonceStr", "packageParam", "signType", "paySign"};
        Arrays.sort(arr);
        Arrays.stream(arr)
                .forEach(str -> System.out.println(str));
    }

}
