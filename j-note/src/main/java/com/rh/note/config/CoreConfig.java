package com.rh.note.config;

import com.rh.note.action.DefaultEventAction;
import com.rh.note.action.MatchAction;
import com.rh.note.action.ProManageAction;
import com.rh.note.action.WorkAction;
import com.rh.note.api.FileAPIService;
import com.rh.note.api.ProManageViewAPI;
import com.rh.note.api.WorkViewAPIService;

/**
 * 配置
 */
public class CoreConfig {
    // 配置 服务 对象
    public static final FileAPIService file_api_service = new FileAPIService();
    public static final ProManageViewAPI pro_manage_view_api_service = new ProManageViewAPI();
    public static final WorkViewAPIService work_view_api_service = new WorkViewAPIService();
    // 配置 业务入口 对象
    public static final ProManageAction pro_manage_action = new ProManageAction()
            .setFileAPIService(file_api_service)
            .setProManageViewAPI(pro_manage_view_api_service);
    public static final WorkAction work_action = new WorkAction()
            .setFileAPIService(file_api_service)
            .setWorkViewAPIService(work_view_api_service);
    public static final MatchAction match_action = new MatchAction();
    public static final DefaultEventAction default_event_action = new DefaultEventAction();
    // 配置桥接项目对象
    static {
        BridgingBeanConfig.setProManageAction(pro_manage_action);
        BridgingBeanConfig.setWorkAction(work_action);
        BridgingBeanConfig.setMatchAction(match_action);
        BridgingBeanConfig.setDefaultEventAction(default_event_action);
    }

}
