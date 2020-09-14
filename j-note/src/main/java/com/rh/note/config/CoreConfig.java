package com.rh.note.config;

import com.rh.note.action.DefaultEventAction;
import com.rh.note.action.OperationAction;
import com.rh.note.action.ProManageAction;
import com.rh.note.action.WorkAction;
import com.rh.note.api.FileService;
import com.rh.note.api.ProManageViewAPI;
import com.rh.note.api.WorkViewService;

/**
 * 配置
 */
public class CoreConfig {
    // 配置 服务 对象
    public static final FileService file_api_service = new FileService();
    public static final ProManageViewAPI pro_manage_view_api_service = new ProManageViewAPI();
    public static final WorkViewService work_view_api_service = new WorkViewService();
    // 配置 业务入口 对象
    public static final WorkAction work_action = new WorkAction()
            .setFileService(file_api_service)
            .setWorkViewService(work_view_api_service);
    public static final ProManageAction pro_manage_action = new ProManageAction()
            .setFileService(file_api_service)
            .setProManageViewAPI(pro_manage_view_api_service)
            .setWorkAction(work_action);
    public static final OperationAction match_action = new OperationAction();
    public static final DefaultEventAction default_event_action = new DefaultEventAction();
    // 配置桥接项目对象
    static {
        BridgingBeanConfig.setProManageAction(pro_manage_action);
        BridgingBeanConfig.setWorkAction(work_action);
        BridgingBeanConfig.setOperationAction(match_action);
        BridgingBeanConfig.setDefaultEventAction(default_event_action);
    }

}
