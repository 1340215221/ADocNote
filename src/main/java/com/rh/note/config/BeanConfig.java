package com.rh.note.config;

import com.rh.note.action.ProjectListAction;
import com.rh.note.action.WorkAction;
import com.rh.note.api.FileServiceAPI;
import com.rh.note.api.ProjectListViewAPI;
import com.rh.note.api.WorkViewAPI;
import com.rh.note.event.ProjectListEvent;
import com.rh.note.event.ProjectManagerMenuEvent;
import com.rh.note.event.TextAreaEvent;
import com.rh.note.event.TitleListEvent;
import com.rh.note.event.WorkFrameEvent;
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
    WorkAction workAction = proxy.getBean(WorkAction.class)
            .setFileServiceAPI(fileServiceApi)
            .setWorkViewAPI(workViewApi);
    ProjectListAction projectListAction = proxy.getBean(ProjectListAction.class)
            .setFileAPIService(fileServiceApi)
            .setProjectListAPI(projectListViewApi)
            .setWorkAction(workAction);
    //------------------------------------------------action------------------------------------------------------------
    //------------------------------------------------event-------------------------------------------------------------
    ProjectListEvent projectListEvent = new ProjectListEvent();
    ProjectManagerMenuEvent projectManagerMenuEvent = new ProjectManagerMenuEvent();
    TextAreaEvent textAreaEvent = new TextAreaEvent();
    TitleListEvent titleListEvent = new TitleListEvent();
    WorkFrameEvent workFrameEvent = new WorkFrameEvent();
    //------------------------------------------------event-------------------------------------------------------------

}
