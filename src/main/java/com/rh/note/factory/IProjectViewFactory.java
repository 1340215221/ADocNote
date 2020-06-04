package com.rh.note.factory;

import com.rh.note.view.ProjectFrame;
import com.rh.note.view.ProjectList;
import com.rh.note.view.ProjectMenu;
import com.rh.note.view.ProjectPanel;
import groovy.lang.Closure;

public interface IProjectViewFactory {

    default Object project_frame(Closure closure) {
        return new ProjectFrame().init(closure);
    }

    default Object project_panel(Closure closure) {
        return new ProjectPanel().init(closure);
    }

    default Object project_list(Closure closure){
        return new ProjectList().init(closure);
    }

    default Object project_menu(Closure closure) {
        return new ProjectMenu().init(closure);
    }
}
