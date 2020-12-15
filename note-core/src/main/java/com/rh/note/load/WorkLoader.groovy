package com.rh.note.load

import com.rh.note.common.ILoader
import com.rh.note.config.WorkConfig
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * 工作窗口工厂
 */
@Component
@Scope("prototype")
class WorkLoader extends WorkConfig implements ILoader {

    WorkLoader(ApplicationContext app) {
        super(app)
    }

    @Override
    void globalSettings() {
    }

    /**
     * 组装组建
     */
    @Override
    void init() {
        work_frame {
            base_panel {
                head_menu_panel {}
                bottom_sidebar_panel {}
                left_sidebar_panel {
                    left_sidebar_tab_panel {
                        title_tree_tab_button {}
                    }
                    title_tree {}
                }
                tabbed_pane {
                    input_prompt_list {}
                }
            }
        }
    }

}
