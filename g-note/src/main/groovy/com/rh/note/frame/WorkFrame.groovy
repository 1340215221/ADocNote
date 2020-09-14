package com.rh.note.frame

import com.rh.note.base.IFrame
import com.rh.note.component.factory.AdocTextPaneFactory
import com.rh.note.component.factory.TitleNavigateButtonFactory
import com.rh.note.component.factory.TitleScrollPaneFactory
import com.rh.note.component.factory.TitleTreeModelFactory
import com.rh.note.component.factory.TitleTreeNodeFactory
import com.rh.note.config.IWorkFrame
import com.rh.note.event.WorkFrameEvent
import com.rh.note.util.ISwingBuilder

import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.AWTEventListener

/**
 * 工作窗口工厂
 */
class WorkFrame implements IWorkFrame, IFrame, ISwingBuilder {

    @Override
    void globalSettings() {
        Toolkit toolkit = Toolkit.getDefaultToolkit()
        toolkit.addAWTEventListener(new AWTEventListener() {
            @Override
            void eventDispatched(AWTEvent event) {
                WorkFrameEvent.saveAllEdited(event)
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
                }
            }
        }
    }

    /**
     * 注册组件工厂
     */
    @Override
    void registerComponent() {
        swingBuilder.registerFactory(TitleTreeModelFactory.name(), new TitleTreeModelFactory())
        swingBuilder.registerFactory(TitleTreeNodeFactory.name(), new TitleTreeNodeFactory())
        swingBuilder.registerFactory(AdocTextPaneFactory.name(), new AdocTextPaneFactory())
        swingBuilder.registerFactory(TitleNavigateButtonFactory.name(), new TitleNavigateButtonFactory())
        swingBuilder.registerFactory(TitleScrollPaneFactory.name(), new TitleScrollPaneFactory())
    }

}
