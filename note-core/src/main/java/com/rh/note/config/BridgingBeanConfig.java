package com.rh.note.config;

import com.rh.note.action.IOperationAction;
import com.rh.note.action.IProManageAction;
import com.rh.note.action.IWorkAction;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 桥接对象实例配置
 */
@Component
public class BridgingBeanConfig implements ApplicationContextAware {
    /**
     * 工作窗口操作入口
     */
    @Autowired
    private static IWorkAction workAction;
    /**
     * 项目管理操作入口
     */
    @Autowired
    private static IProManageAction proManageAction;
    /**
     * 匹配操作入口
     */
    @Autowired
    private static IOperationAction operationAction;

    /**
     * 工作窗口入口
     */
    public static IWorkAction workAction() {
        return workAction;
    }

    /**
     * 项目管理操作入口
     */
    public static IProManageAction proManageAction() {
        return proManageAction;
    }

    /**
     * 匹配操作入口
     */
    public static IOperationAction operationAction() {
        return operationAction;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("ok");
    }
}
