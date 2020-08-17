package com.rh.note;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.rh.note.config.BeanConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 笔记软件启动程序
 */
public class NoteApplication {

    private static Logger log = LoggerFactory.getLogger(NoteApplication.class);

    public static void main(String[] args) {
        log.info("启动笔记软件");
        FlatDarculaLaf.install();
        log.info("完成Darcula主题初始化");
        BeanConfig.projectListAction.startFrame();
        log.info("完成[笔记APP]初始化");
    }

}
