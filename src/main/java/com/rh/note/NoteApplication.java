package com.rh.note;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.rh.note.config.BeanConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 笔记软件启动程序
 */
@Slf4j
public class NoteApplication {

    public static void main(String[] args) {
        log.info("启动笔记软件");
        FlatDarculaLaf.install();
        log.info("完成Darcula主题初始化");
        BeanConfig.projectListAction.startFrame();
        log.info("完成[笔记APP]初始化");
    }

}
