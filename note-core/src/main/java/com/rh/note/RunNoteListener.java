package com.rh.note;

import com.rh.note.action.ProManageAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 启动笔记管理窗口
 */
@Component
public class RunNoteListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ProManageAction proManageAction;
    @Autowired
    private ApplicationContext mainContext;

    /**
     * 在spring容器初始化完成后执行
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 只在主容器启动时加载
        if (mainContext.equals(event.getApplicationContext())) {
            proManageAction.loadNewFrameContext();
        }
    }
}
