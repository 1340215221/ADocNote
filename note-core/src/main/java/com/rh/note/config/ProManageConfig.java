package com.rh.note.config;

import com.rh.note.builder.HistoryProListBuilder;
import com.rh.note.builder.ImportProButtonBuilder;
import com.rh.note.builder.ProListPanelBuilder;
import com.rh.note.builder.ProManageFrameBuilder;
import com.rh.note.builder.ProMenuPanelBuilder;
import groovy.lang.Closure;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public abstract class ProManageConfig implements ApplicationContextAware {

    /**
     * todo 这些控件需要从子容器中获取
     */
    private ApplicationContext app;

    protected void project_management_frame(Closure children) {
        get(ProManageFrameBuilder.class).init(children);
    }

    protected void project_list_panel(Closure children) {
        get(ProListPanelBuilder.class).init(children);
    }

    protected void history_project_list(Closure children){
        get(HistoryProListBuilder.class).init(children);
    }

    protected void project_menu_panel(Closure children) {
        get(ProMenuPanelBuilder.class).init(children);
    }

    protected void import_project_button(Closure children) {
        get(ImportProButtonBuilder.class).init(children);
    }

    private <T> @NotNull T get(Class<T> clazz) {
        return app.getBean(clazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext app) throws BeansException {
        if (this.app == null) {
            this.app = app;
        }
    }
}
