package com.rh.note;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.rh.note.view.MainFrame;

/**
 * 笔记软件启动程序
 */
public class NoteApplication {

    public static void main(String[] args) {
        System.out.println("启动笔记软件");
        FlatDarculaLaf.install();
        System.out.println("完成Darcula主题初始化");
        new MainFrame().init();
        System.out.println("完成[笔记APP]初始化");
    }

}
