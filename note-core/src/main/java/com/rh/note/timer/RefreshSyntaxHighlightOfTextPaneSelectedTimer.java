package com.rh.note.timer;

import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.event.TabbedPaneEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 刷新被选择编辑区的语法高亮 定时任务
 */
@ComponentBean(FrameCategoryEnum.WORK)
public class RefreshSyntaxHighlightOfTextPaneSelectedTimer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ApplicationContext currentContext;
    @Autowired
    private TabbedPaneEvent event;
    /**
     * 定时任务
     */
    private Timer timer;

    @PostConstruct
    public void init() {
        timer = new Timer(2000, new ActionListenerImpl());
        timer.setInitialDelay(0);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (currentContext.equals(event.getApplicationContext())) {
            timer.start();
        }
    }

    /**
     * 立即执行
     */
    public void cancelOnceRun() {
        timer.setInitialDelay(2000);
        timer.restart();
    }

    @PreDestroy
    public void destroy() {
        timer.stop();
    }

    public class ActionListenerImpl implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            event.refresh_syntax_highlight();
        }
    }

}
