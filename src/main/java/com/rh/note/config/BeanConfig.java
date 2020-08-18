package com.rh.note.config;

import com.rh.note.action.IProjectListAction;
import com.rh.note.action.IWorkAction;
import com.rh.note.action.ProjectListAction;
import com.rh.note.action.WorkAction;
import com.rh.note.api.FileServiceAPI;
import com.rh.note.api.ProjectListViewAPI;
import com.rh.note.api.WorkViewAPI;
import com.rh.note.util.aop.ProxyUtil;

/**
 * 对象配置
 */
public interface BeanConfig {

    ProxyUtil proxy = new ProxyUtil();
    //------------------------------------------------api---------------------------------------------------------------
    FileServiceAPI fileServiceApi = new FileServiceAPI();
    ProjectListViewAPI projectListViewApi = new ProjectListViewAPI();
    WorkViewAPI workViewApi = new WorkViewAPI();
    //------------------------------------------------api---------------------------------------------------------------
    //------------------------------------------------action------------------------------------------------------------
    IWorkAction workAction = new WorkAction();
    //    IWorkAction workAction = proxy.getBean(WorkAction.class)
//            .setFileServiceAPI(fileServiceApi)
//            .setWorkViewAPI(workViewApi);
    IProjectListAction projectListAction = new ProjectListAction();
//    IProjectListAction projectListAction = proxy.getBean(ProjectListAction.class)
//            .setFileAPIService(fileServiceApi)
//            .setProjectListAPI(projectListViewApi)
//            .setWorkAction(workAction);
    //------------------------------------------------action------------------------------------------------------------

}
