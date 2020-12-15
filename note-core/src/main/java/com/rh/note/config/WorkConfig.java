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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

@RequiredArgsConstructor
public abstract class WorkConfig {

    @NonNull
    private ApplicationContext app;

    protected void work_frame(Closure children) {
        get(WorkFrameBuilder.class).init(children);
    }

    protected   void base_panel(Closure children) {
        get(BasePanelBuilder.class).init(children);
    }

    protected void head_menu_panel(Closure children) {
        get(HeadMenuPanelBuilder.class).init(children);
    }

    protected void bottom_sidebar_panel(Closure children) {
        get(BottomSidebarPanelBuilder.class).init(children);
    }

    protected void left_sidebar_panel(Closure children) {
        get(LeftSidebarPanelBuilder.class).init(children);
    }

    protected void left_sidebar_tab_panel(Closure children) {
        get(LeftSidebarTabPanelBuilder.class).init(children);
    }

    protected void title_tree_tab_button(Closure children) {
        get(TitleTreeTabButtonBuilder.class).init(children);
    }

    protected void title_tree(Closure children) {
        get(TitleTreeBuilder.class).init(children);
    }

    protected void tabbed_pane(Closure children) {
        get(TabbedPaneBuilder.class).init(children);
    }

    protected void input_prompt_list(Closure children) {
        get(InputPromptListBuilder.class).init(children);
    }

    private <T> @NotNull T get(Class<T> clazz) {
        return app.getBean(clazz);
    }

}
