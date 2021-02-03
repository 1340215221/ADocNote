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
    }

}
