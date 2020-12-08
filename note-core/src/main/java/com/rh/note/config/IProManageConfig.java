package com.rh.note.config;

import com.rh.note.builder.HistoryProListBuilder;
import com.rh.note.builder.ImportProButtonBuilder;
import com.rh.note.builder.ProListPanelBuilder;
import com.rh.note.builder.ProManageFrameBuilder;
import com.rh.note.builder.ProMenuPanelBuilder;
import groovy.lang.Closure;

import static com.rh.note.util.SpringUtil.get;

public interface IProManageConfig {

    default void project_management_frame(Closure children) {
        get(ProManageFrameBuilder.class).init(children);
    }

    default void project_list_panel(Closure children) {
        get(ProListPanelBuilder.class).init(children);
    }

    default void history_project_list(Closure children){
        get(HistoryProListBuilder.class).init(children);
    }

    default void project_menu_panel(Closure children) {
        get(ProMenuPanelBuilder.class).init(children);
    }

    default void import_project_button(Closure children) {
        get(ImportProButtonBuilder.class).init(children);
    }

}
