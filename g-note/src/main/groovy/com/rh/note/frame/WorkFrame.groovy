package com.rh.note.frame

import com.rh.note.base.IFrame
import com.rh.note.base.ISwingBuilder
import com.rh.note.component.factory.AdocScrollPaneFactory
import com.rh.note.component.factory.AdocTextPaneFactory
import com.rh.note.component.factory.DefaultTreeModelFactory
import com.rh.note.component.factory.TitleButtonFactory
import com.rh.note.component.factory.TitleTreeNodeFactory
import com.rh.note.config.IWorkConfig
import com.rh.note.event.WorkFrameEvent

import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.AWTEventListener

/**
 * 工作窗口工厂
 */
class WorkFrame implements IWorkConfig, IFrame, ISwingBuilder {

    @Override
    void globalSettings() {
        Toolkit toolkit = Toolkit.getDefaultToolkit()
        toolkit.addAWTEventListener(new AWTEventListener() {
            @Override
            void eventDispatched(AWTEvent event) {
                WorkFrameEvent.save_all_edited(event)
                WorkFrameEvent.git_commit_adoc(event)
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
        swingBuilder.registerFactory(DefaultTreeModelFactory.name(), new DefaultTreeModelFactory())
        swingBuilder.registerFactory(TitleTreeNodeFactory.name(), new TitleTreeNodeFactory())
        swingBuilder.registerFactory(AdocTextPaneFactory.name(), new AdocTextPaneFactory())
        swingBuilder.registerFactory(TitleButtonFactory.name(), new TitleButtonFactory())
        swingBuilder.registerFactory(AdocScrollPaneFactory.name(), new AdocScrollPaneFactory())
    }

}
