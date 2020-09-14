package com.rh.note.config;

import com.rh.note.action.IDefaultEventAction;
import com.rh.note.action.IOperationAction;
import com.rh.note.action.IProjectManagementAction;
import com.rh.note.action.IWorkAction;

/**
 * 桥接对象实例配置
 */
public class BridgingBeanConfig {
    /**
     * 工作窗口操作入口
     */
    private static IWorkAction workAction;
    /**
     * 项目管理操作入口
     */
    private static IProjectManagementAction proManageAction;
    /**
     * 匹配操作入口
     */
    private static IOperationAction operationAction;
    /**
     * 默认事件操作入口
     */
    private static IDefaultEventAction defaultEventAction;

    public static void setWorkAction(IWorkAction workAction) {
        if (BridgingBeanConfig.workAction == null) {
            BridgingBeanConfig.workAction = workAction;
        }
    }

    /**
     * 工作窗口入口
     */
    public static IWorkAction workAction() {
        return workAction;
    }

    public static void setProManageAction(IProjectManagementAction proManageAction) {
        if (BridgingBeanConfig.proManageAction == null) {
            BridgingBeanConfig.proManageAction = proManageAction;
        }
    }

    /**
     * 项目管理操作入口
     */
    public static IProjectManagementAction proManageAction() {
        return proManageAction;
    }

    public static void setOperationAction(IOperationAction operationAction) {
        if (BridgingBeanConfig.operationAction == null) {
            BridgingBeanConfig.operationAction = operationAction;
        }
    }

    /**
     * 匹配操作入口
     */
    public static IOperationAction operationAction() {
        return operationAction;
    }

    public static void setDefaultEventAction(IDefaultEventAction defaultEventAction) {
        if (BridgingBeanConfig.defaultEventAction == null) {
            BridgingBeanConfig.defaultEventAction = defaultEventAction;
        }
    }

    /**
     * 匹配操作入口
     */
    public static IDefaultEventAction defaultEventAction() {
        return defaultEventAction;
    }

}
