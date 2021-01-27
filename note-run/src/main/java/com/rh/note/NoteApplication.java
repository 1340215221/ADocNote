package com.rh.note;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 笔记软件启动程序
 */
@EnableAsync
@PropertySource(value= {"classpath:config.yml"}, ignoreResourceNotFound = true)
@SpringBootApplication
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(NoteApplication.class);
        builder.headless(false).run(args);
    }

}
