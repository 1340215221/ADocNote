package com.rh.note.view;

import com.rh.note.builder.ProjectListBuilder;
import com.rh.note.model.component.Init;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.ArrayUtils;

import javax.swing.*;

/**
 * 项目列表
 */
public class ProjectListView extends Init<JList<RecentlyOpenedRecordVO>> {

    public ProjectListView init() {
        return super.init(ProjectListBuilder.getId());
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

    /**
     * 加载项目列表
     */
    public void loadProjectList(RecentlyOpenedRecordVO[] projectInfos) {
        if (ArrayUtils.isEmpty(projectInfos)) {
            return;
        }
        projectList().setListData(projectInfos);
    }
}
