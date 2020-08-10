package com.rh.note.build;

import com.rh.note.view.ProjectListFrame;
import com.rh.note.view.ProjectList;
import com.rh.note.view.ProjectMenu;
import com.rh.note.view.ProjectListPanel;
import groovy.lang.Closure;

/**
 * 构建项目列表组建
 */
public interface ProjectListFrameBuild {

    static void project_frame(Closure children) {
        new ProjectListFrame().init(children);
    }

    static void project_panel(Closure closure) {
        new ProjectListPanel().init(closure);
    }

    static void project_list(Closure closure){
        new ProjectList().init(closure);
    }

    static void project_menu(Closure closure) {
        new ProjectMenu().init(closure);
    }

}
