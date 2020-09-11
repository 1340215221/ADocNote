package com.rh.note.frame

import com.rh.note.base.IFrame
import com.rh.note.config.IProjectListFrame

/**
 * 项目列表
 */
class ProjectManageFrame implements IFrame, IProjectListFrame {

    @Override
    void init() {
        project_management_frame {
            project_list_panel {
                history_project_list{}
                project_menu_panel{
                    import_project_button{}
                }
            }
        }
    }

    @Override
    void globalSettings() {
    }

    @Override
    void registerComponent() {
    }
}
