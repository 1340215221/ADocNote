package com.rh.note.config;

import com.rh.note.builder.ImportProjectButtonBuilder;
import com.rh.note.builder.HistoryProjectListBuilder;
import com.rh.note.builder.ProjectManagementFrameBuilder;
import com.rh.note.builder.ProjectListPanelBuilder;
import com.rh.note.builder.ProjectMenuPanelBuilder;
import groovy.lang.Closure;

/**
 * 构建项目列表组建
 */
public interface IProjectListFrame {

    default void project_management_frame(Closure children) {
        new ProjectManagementFrameBuilder().init(children);
    }

    default void project_list_panel(Closure closure) {
        new ProjectListPanelBuilder().init(closure);
    }

    default void history_project_list(Closure closure){
        new HistoryProjectListBuilder().init(closure);
    }

    default void project_menu_panel(Closure closure) {
        new ProjectMenuPanelBuilder().init(closure);
    }

    default void import_project_button(Closure closure) {
        new ImportProjectButtonBuilder().init(closure);
    }

}
