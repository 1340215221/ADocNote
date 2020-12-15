package com.rh.note.load


import com.rh.note.common.ILoader
import com.rh.note.config.ProManageConfig
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * 项目列表
 */
@Component
@Scope("prototype")
class ProManageLoader extends ProManageConfig implements ILoader {

    ProManageLoader(ApplicationContext app) {
        super(app)
    }

    void newAppContext() {
    }

    @Override
    void init() {
        project_management_frame {
            project_list_panel {
                history_project_list {}
                project_menu_panel {
                    import_project_button {}
                }
            }
        }
    }

    @Override
    void globalSettings() {
    }

}
