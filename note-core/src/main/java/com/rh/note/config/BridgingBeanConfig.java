package com.rh.note.config;

import com.rh.note.action.IOperationAction;
import com.rh.note.action.IProManageAction;
import com.rh.note.action.IWorkAction;
import org.springframework.stereotype.Component;

/**
 * 桥接对象实例配置
 */
@Component
public class BridgingBeanConfig {
    /**
     * 工作窗口操作入口
     */
    private static IWorkAction workAction;
    /**
     * 项目管理操作入口
     */
    private static IProManageAction proManageAction;
    /**
     * 匹配操作入口
     */
    private static IOperationAction operationAction;

    public BridgingBeanConfig(IWorkAction workAction, IProManageAction proManageAction, IOperationAction operationAction) {
        BridgingBeanConfig.workAction = workAction;
        BridgingBeanConfig.proManageAction = proManageAction;
        BridgingBeanConfig.operationAction = operationAction;
    }

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

}
