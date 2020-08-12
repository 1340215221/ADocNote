package com.rh.note.model.component;

import com.rh.note.view.ProjectList;
import com.rh.note.vo.RecentlyOpenedRecordVO;

import javax.swing.*;

/**
 * 项目列表
 */
public class ProjectListImpl extends Init<JList<RecentlyOpenedRecordVO>> {

    public ProjectListImpl init() {
        return super.init(ProjectList.getId());
    }

    /**
     * 获得被选择项目路径
     */
    public String getSelectedProject() {
        RecentlyOpenedRecordVO vo = projectList().getSelectedValue();
        if (vo == null) {
            return null;
        }
        return vo.getProjectPath();
    }

    private JList<RecentlyOpenedRecordVO> projectList() {
        return getBean();
    }
}
