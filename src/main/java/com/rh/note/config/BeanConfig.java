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

/**
 * 对象配置
 */
public interface BeanConfig {

    //------------------------------------------------api---------------------------------------------------------------
    FileServiceAPI fileServiceApi = new FileServiceAPI();
    ProjectListViewAPI projectListViewApi = new ProjectListViewAPI();
    WorkViewAPI workViewApi = new WorkViewAPI();
    //------------------------------------------------api---------------------------------------------------------------
    //------------------------------------------------action------------------------------------------------------------
    WorkAction workAction = new WorkAction()
            .setFileServiceAPI(fileServiceApi)
            .setWorkViewAPI(workViewApi);
    ProjectListAction projectListAction = new ProjectListAction()
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
