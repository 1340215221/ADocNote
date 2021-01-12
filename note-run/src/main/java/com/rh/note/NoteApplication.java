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
@ComponentScan(basePackages = {
        "com.rh.note.main", // 核心包入口程序
        "com.rh.note.api", // 业务模块聚合层
        "com.rh.note.action", // 前后端交互聚合层
        "com.rh.note.aspect", // 切面, 用于设置当前操作的子容器
        "com.rh.note.load", // 用于加载子容器控件
        "com.rh.note.util", // 工具类
        "com.rh.note.config", // 配置类
})
public class NoteApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(NoteApplication.class);
        ConfigurableApplicationContext app = builder.headless(false).run(args);
        NoteMain main = app.getBean(NoteMain.class);
        main.init();
    }

}
