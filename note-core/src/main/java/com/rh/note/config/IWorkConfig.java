package com.rh.note.config;

import com.rh.note.builder.BasePanelBuilder;
import com.rh.note.builder.BottomSidebarPanelBuilder;
import com.rh.note.builder.HeadMenuPanelBuilder;
import com.rh.note.builder.InputPromptListBuilder;
import com.rh.note.builder.LeftSidebarPanelBuilder;
import com.rh.note.builder.LeftSidebarTabPanelBuilder;
import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.builder.TitleTreeTabButtonBuilder;
import com.rh.note.builder.WorkFrameBuilder;
import groovy.lang.Closure;

import static com.rh.note.util.SpringUtil.get;

public interface IWorkConfig {

    default void work_frame(Closure children) {
        get(WorkFrameBuilder.class).init(children);
    }

    default void base_panel(Closure children) {
        get(BasePanelBuilder.class).init(children);
    }

    default void head_menu_panel(Closure children) {
        get(HeadMenuPanelBuilder.class).init(children);
    }

    default void bottom_sidebar_panel(Closure children) {
        get(BottomSidebarPanelBuilder.class).init(children);
    }

    default void left_sidebar_panel(Closure children) {
        get(LeftSidebarPanelBuilder.class).init(children);
    }

    default void left_sidebar_tab_panel(Closure children) {
        get(LeftSidebarTabPanelBuilder.class).init(children);
    }

    default void title_tree_tab_button(Closure children) {
        get(TitleTreeTabButtonBuilder.class).init(children);
    }

    default void title_tree(Closure children) {
        get(TitleTreeBuilder.class).init(children);
    }

    default void tabbed_pane(Closure children) {
        get(TabbedPaneBuilder.class).init(children);
    }

    default void input_prompt_list(Closure children) {
        get(InputPromptListBuilder.class).init(children);
    }

}
