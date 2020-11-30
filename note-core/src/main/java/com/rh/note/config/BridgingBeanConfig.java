package com.rh.note.config;

import com.rh.note.action.IOperationAction;
import com.rh.note.action.IProManageAction;
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
    private static IProManageAction proManageAction;
    /**
     * 匹配操作入口
     */
    private static IOperationAction operationAction;

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

    public static void setProManageAction(IProManageAction proManageAction) {
        if (BridgingBeanConfig.proManageAction == null) {
            BridgingBeanConfig.proManageAction = proManageAction;
        }
    }

    /**
     * 项目管理操作入口
     */
    public static IProManageAction proManageAction() {
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

}
