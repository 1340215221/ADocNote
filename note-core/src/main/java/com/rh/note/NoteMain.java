package com.rh.note;

import com.formdev.flatlaf.FlatDarculaLaf;
import lombok.extern.slf4j.Slf4j;

import static com.rh.note.config.CoreConfig.pro_manage_action;

/**
 * 笔记 启动类
 */
@Slf4j
public class NoteMain {

    public void init() {
        log.info("启动笔记软件");
        FlatDarculaLaf.install();
        log.info("完成Darcula主题初始化");
        pro_manage_action.startFrame();
        log.info("完成[笔记APP]初始化");
    }

}
