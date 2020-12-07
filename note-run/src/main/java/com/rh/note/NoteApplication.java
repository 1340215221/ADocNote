package com.rh.note;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 笔记软件启动程序
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.rh.note"})
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(NoteApplication.class);
        ConfigurableApplicationContext app = builder.headless(false).run(args);
        NoteMain main = app.getBean(NoteMain.class);
        main.init();
    }

}
