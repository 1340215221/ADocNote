package com.rh.note.config;

import com.rh.note.builder.HistoryProListBuilder;
import com.rh.note.builder.ImportProButtonBuilder;
import com.rh.note.builder.ProListPanelBuilder;
import com.rh.note.builder.ProManageFrameBuilder;
import com.rh.note.builder.ProMenuPanelBuilder;
import groovy.lang.Closure;

public interface IProManageConfig {

    default void project_management_frame(Closure children) {
        new ProManageFrameBuilder().init(children);
    }

    default void project_list_panel(Closure closure) {
        new ProListPanelBuilder().init(closure);
    }

    default void history_project_list(Closure closure){
        new HistoryProListBuilder().init(closure);
    }

    default void project_menu_panel(Closure closure) {
        new ProMenuPanelBuilder().init(closure);
    }

    default void import_project_button(Closure closure) {
        new ImportProButtonBuilder().init(closure);
    }

}
