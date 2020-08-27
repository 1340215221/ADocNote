package com.rh.note.config;

import com.rh.note.action.IProjectListAction;
import com.rh.note.action.IWorkAction;

public class ActionConfig {

    public static ActionConfig get = new ActionConfig();
    private IProjectListAction projectListAction;
    private IWorkAction workAction;

    public ActionConfig setProjectListAction(IProjectListAction projectListAction) {
        if (this.projectListAction == null) {
            this.projectListAction = projectListAction;
        }
        return this;
    }

    public IProjectListAction projectListAction() {
        return projectListAction;
    }

    public ActionConfig setWorkAction(IWorkAction workAction) {
        if (this.workAction == null) {
            this.workAction = workAction;
        }
        return this;
    }

    public IWorkAction workAction() {
        return workAction;
    }

}
