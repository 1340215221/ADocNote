package com.rh.note.config;

import com.rh.note.action.ProjectListAction;
import com.rh.note.action.WorkAction;
import com.rh.note.api.FileServiceAPI;
import com.rh.note.api.ProjectListViewAPI;
import com.rh.note.api.WorkViewAPI;
import com.rh.note.util.aop.ProxyUtil;

/**
 * 对象配置
 */
public class BeanConfig {

    public static ProxyUtil proxy = new ProxyUtil();
    //------------------------------------------------api---------------------------------------------------------------
    public static FileServiceAPI fileServiceApi = new FileServiceAPI();
    public static ProjectListViewAPI projectListViewApi = new ProjectListViewAPI();
    public static WorkViewAPI workViewApi = new WorkViewAPI();
    //------------------------------------------------api---------------------------------------------------------------
    //------------------------------------------------action------------------------------------------------------------
    public static WorkAction workAction = proxy.getBean(WorkAction.class);
    public static ProjectListAction projectListAction = proxy.getBean(ProjectListAction.class);
    //------------------------------------------------action------------------------------------------------------------
    static {
        ActionConfig.get.setProjectListAction(projectListAction);
        ActionConfig.get.setWorkAction(workAction);
    }

}
