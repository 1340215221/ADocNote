package com.rh.note;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 笔记软件启动程序
 */
@SpringBootApplication
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(NoteApplication.class);
        builder.headless(false).run(args);
        new NoteMain().init();
    }

    /**
     *
     log.info("启动笔记软件");
     FlatDarculaLaf.install();
     log.info("完成Darcula主题初始化");
     pro_manage_action.startFrame();
     log.info("完成[笔记APP]初始化");
     */

}
