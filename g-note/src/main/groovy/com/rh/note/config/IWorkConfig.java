package com.rh.note.config;

import com.rh.note.builder.BasePanelBuilder;
import com.rh.note.builder.BottomSidebarPanelBuilder;
import com.rh.note.builder.HeadMenuPanelBuilder;
import com.rh.note.builder.LeftSidebarPanelBuilder;
import com.rh.note.builder.LeftSidebarTabPanelBuilder;
import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.builder.TitleTreeTabButtonBuilder;
import com.rh.note.builder.WorkFrameBuilder;
import groovy.lang.Closure;

public interface IWorkConfig {

    default void work_frame(Closure children) {
        new WorkFrameBuilder().init(children);
    }

    default void base_panel(Closure children) {
        new BasePanelBuilder().init(children);
    }

    default void head_menu_panel(Closure children) {
        new HeadMenuPanelBuilder().init(children);
    }

    default void bottom_sidebar_panel(Closure children) {
        new BottomSidebarPanelBuilder().init(children);
    }

    default void left_sidebar_panel(Closure children) {
        new LeftSidebarPanelBuilder().init(children);
    }

    default void left_sidebar_tab_panel(Closure children) {
        new LeftSidebarTabPanelBuilder().init(children);
    }

    default void title_tree_tab_button(Closure children) {
        new TitleTreeTabButtonBuilder().init(children);
    }

    default void title_tree(Closure children) {
        new TitleTreeBuilder().init(children);
    }

    default void tabbed_pane(Closure children) {
        new TabbedPaneBuilder().init(children);
    }

}
