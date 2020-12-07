package com.rh.note;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.rh.note.action.IProManageAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 笔记 启动类
 */
@Slf4j
@Component
public class NoteMain {

    @Autowired
    private IProManageAction proManageAction;

    public void init() {
        log.info("启动笔记软件");
        FlatDarculaLaf.install();
        log.info("完成Darcula主题初始化");
        proManageAction.startFrame();
        log.info("完成[笔记APP]初始化");
    }

}
