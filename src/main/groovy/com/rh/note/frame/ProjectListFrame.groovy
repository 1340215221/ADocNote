package com.rh.note.frame

import com.rh.note.common.IFrame
import com.rh.note.config.IProjectListFrame

/**
 * 项目列表
 */
class ProjectListFrame implements IFrame, IProjectListFrame {

    @Override
    void init() {
        project_frame {
            project_panel {
                project_list{}
                project_menu{
                    openProjectButton{}
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
