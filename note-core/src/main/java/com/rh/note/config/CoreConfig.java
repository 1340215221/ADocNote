package com.rh.note.config;

import com.rh.note.action.OperationAction;
import com.rh.note.action.ProManageAction;
import com.rh.note.action.WorkAction;
import com.rh.note.api.FileServiceApi;
import com.rh.note.api.GitServiceApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;

/**
 * 配置
 */
public class CoreConfig {
    // 配置 服务 对象
    public static final FileServiceApi file_api_service = new FileServiceApi();
    public static final ProManageViewApi pro_manage_view_api_service = new ProManageViewApi();
    public static final WorkViewApi work_view_api_service = new WorkViewApi();
    public static final GitServiceApi git_service_api = new GitServiceApi();
    // 配置 业务入口 对象
    public static final WorkAction work_action = new WorkAction()
            .setFileServiceApi(file_api_service)
            .setWorkViewApi(work_view_api_service)
            .setGitServiceApi(git_service_api);
    public static final ProManageAction pro_manage_action = new ProManageAction()
            .setFileServiceApi(file_api_service)
            .setProManageViewApi(pro_manage_view_api_service)
            .setWorkAction(work_action);
    public static final OperationAction match_action = new OperationAction()
            .setWorkViewApi(work_view_api_service)
            .setProManageViewApi(pro_manage_view_api_service)
            .setFileServiceApi(file_api_service);
    // 配置桥接项目对象
    static {
        BridgingBeanConfig.setProManageAction(pro_manage_action);
        BridgingBeanConfig.setWorkAction(work_action);
        BridgingBeanConfig.setOperationAction(match_action);
    }

}
