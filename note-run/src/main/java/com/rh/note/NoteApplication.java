package com.rh.note;

import com.rh.note.main.NoteMain;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 笔记软件启动程序
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.rh.note.main", "com.rh.note.api", "com.rh.note.action", "com.rh.note.aspect"})
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(NoteApplication.class);
        ConfigurableApplicationContext app = builder.headless(false).run(args);
        NoteMain main = app.getBean(NoteMain.class);
        main.init();
    }

}
