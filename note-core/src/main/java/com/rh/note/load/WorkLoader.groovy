package com.rh.note.load

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ILoader
import com.rh.note.config.WorkConfig
import com.rh.note.event.WorkFrameEvent
import org.springframework.beans.factory.annotation.Autowired

import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.AWTEventListener

/**
 * 工作窗口工厂
 */
@WorkSingleton
class WorkLoader extends WorkConfig implements ILoader {

    @Autowired
    private WorkFrameEvent workFrameEvent

    @Override
    void globalSettings() {
        Toolkit toolkit = Toolkit.getDefaultToolkit()
        toolkit.addAWTEventListener(new AWTEventListener() {
            @Override
            void eventDispatched(AWTEvent event) {
                workFrameEvent.save_all_edited(event)
                workFrameEvent.git_commit_adoc(event)
            }
        }, AWTEvent.KEY_EVENT_MASK)
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
