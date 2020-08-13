package com.rh.note;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.rh.note.config.BeanConfig;

/**
 * 笔记软件启动程序
 */
public class NoteApplication implements BeanConfig {

    public static void main(String[] args) {
        System.out.println("启动笔记软件");
        FlatDarculaLaf.install();
        System.out.println("完成Darcula主题初始化");
        projectListAction.startFrame();
        System.out.println("完成[笔记APP]初始化");
    }

}
