package com.rh.note.model.component;

import com.rh.note.view.ProjectChooser;

/**
 * 选择项目文件夹
 */
public class ProjectChooserImpl {

    public String showOpenDialog() {
        ProjectListImpl projectList = new ProjectListImpl().init();
        return new ProjectChooser().showOpenDialog(projectList.getBean());
    }

}
