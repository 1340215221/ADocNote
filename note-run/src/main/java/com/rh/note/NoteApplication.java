package com.rh.note;

import com.rh.note.config.BridgingBeanConfig;
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
        System.out.println(app.getBean(BridgingBeanConfig.class));
        new NoteMain().init();
    }

}
