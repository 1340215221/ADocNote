package com.rh.note.load

import com.rh.note.annotation.ProManageSingleton
import com.rh.note.common.ILoader
import com.rh.note.config.ProManageConfig

/**
 * 项目列表
 */
@ProManageSingleton
class ProManageLoader extends ProManageConfig implements ILoader {

    /**
     * todo 加载一个子容器
     */
    void newAppContext() {
    }

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

}
