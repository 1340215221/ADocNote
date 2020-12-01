package com.rh.note;

import com.formdev.flatlaf.FlatDarculaLaf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static com.rh.note.config.BridgingBeanConfig.proManageAction;

/**
 * 笔记 启动类
 */
@Slf4j
@Component
public class NoteMain implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("1111");
    }

    public void init() {
        log.info("启动笔记软件");
        FlatDarculaLaf.install();
        log.info("完成Darcula主题初始化");
        proManageAction().startFrame();
        log.info("完成[笔记APP]初始化");
    }

}
