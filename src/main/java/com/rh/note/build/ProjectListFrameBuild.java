package com.rh.note.build;

import com.rh.note.builder.OpenProjectButtonBuilder;
import com.rh.note.builder.ProjectListFrameBuilder;
import com.rh.note.builder.ProjectListBuilder;
import com.rh.note.builder.ProjectMenuBuilder;
import com.rh.note.builder.ProjectListPanelBuilder;
import groovy.lang.Closure;

/**
 * 构建项目列表组建
 */
public interface ProjectListFrameBuild {

    static void project_frame(Closure children) {
        new ProjectListFrameBuilder().init(children);
    }

    static void project_panel(Closure closure) {
        new ProjectListPanelBuilder().init(closure);
    }

    static void project_list(Closure closure){
        new ProjectListBuilder().init(closure);
    }

    static void project_menu(Closure closure) {
        new ProjectMenuBuilder().init(closure);
    }

    static void openProjectButton(Closure closure) {
        new OpenProjectButtonBuilder().init(closure);
    }

}
