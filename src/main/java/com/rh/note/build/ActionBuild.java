package com.rh.note.build;

import com.rh.note.action.ProjectListAction;
import com.rh.note.action.WorkAction;

public interface ActionBuild {
    WorkAction workAction = new WorkAction();
    ProjectListAction projectListAction = new ProjectListAction();
}
