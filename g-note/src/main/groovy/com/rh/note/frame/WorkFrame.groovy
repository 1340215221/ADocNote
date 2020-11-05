package com.rh.note.frame

import com.rh.note.base.IFrame
import com.rh.note.base.ISwingBuilder
import com.rh.note.component.AdocScrollPane
import com.rh.note.component.AdocTextPane
import com.rh.note.component.InputPromptMenuItem
import com.rh.note.component.TitleButton
import com.rh.note.component.factory.AdocScrollPaneFactory
import com.rh.note.component.factory.DefaultTreeModelFactory
import com.rh.note.component.factory.TitleTreeNodeFactory
import com.rh.note.config.IWorkConfig
import com.rh.note.event.WorkFrameEvent
import groovy.swing.factory.RichActionWidgetFactory
import groovy.swing.factory.TextArgWidgetFactory

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
                    input_prompt_list {}
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
        swingBuilder.registerFactory(AdocScrollPane.NAME, new AdocScrollPaneFactory())

        swingBuilder.registerFactory(AdocTextPane.NAME, new TextArgWidgetFactory(AdocTextPane))
        swingBuilder.registerFactory(TitleButton.NAME, new RichActionWidgetFactory(TitleButton))
        swingBuilder.registerFactory(InputPromptMenuItem.NAME, new RichActionWidgetFactory(InputPromptMenuItem))
    }

}
