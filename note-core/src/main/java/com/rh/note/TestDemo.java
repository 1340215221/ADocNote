package com.rh.note;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;

/**
 * 空的main方法, 用于编译项目
 */
public class TestDemo {

    public static void main(String[] args) {
        System.out.println("和鑫鑫谈的时间: " + DateUtil.between(DateUtil.parseDate("2019-10-29"), DateUtil.parseDate("2020-05-10"), DateUnit.DAY));
        System.out.println("和刘谈的时间: " + DateUtil.between(DateUtil.parseDate("2020-05-29"), DateUtil.parseDate("2021-01-18"), DateUnit.DAY));
    }

}
