package com.rh.note.frame

import com.rh.note.common.IFrame
import com.rh.note.config.IProManageConfig
import org.springframework.stereotype.Component

/**
 * 项目列表
 */
@Component
class ProjectManageFrame implements IFrame, IProManageConfig {

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
