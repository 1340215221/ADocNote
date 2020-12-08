package com.rh.note.frame

import com.rh.note.common.IFrame
import com.rh.note.component.AdocScrollPane
import com.rh.note.component.AdocTextPane
import com.rh.note.component.InputPromptMenuItem
import com.rh.note.component.JavaScrollPane
import com.rh.note.component.JavaTextPane
import com.rh.note.component.TitleButton
import com.rh.note.component.factory.AdocScrollPaneFactory
import com.rh.note.component.factory.DefaultTreeModelFactory
import com.rh.note.component.factory.JavaScrollPaneFactory
import com.rh.note.component.factory.TitleTreeNodeFactory
import com.rh.note.config.IWorkConfig
import com.rh.note.event.WorkFrameEvent
import groovy.swing.SwingBuilder
import groovy.swing.factory.RichActionWidgetFactory
import groovy.swing.factory.TextArgWidgetFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.AWTEventListener

/**
 * 工作窗口工厂
 */
@Component
class WorkFrame implements IWorkConfig, IFrame {

    @Autowired
    private WorkFrameEvent workFrameEvent
    @Autowired
    private SwingBuilder swingBuilder

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

    /**
     * 注册组件工厂
     */
    @Override
    void registerComponent() {
        swingBuilder.registerFactory(DefaultTreeModelFactory.name(), new DefaultTreeModelFactory())
        swingBuilder.registerFactory(TitleTreeNodeFactory.name(), new TitleTreeNodeFactory())
        swingBuilder.registerFactory(AdocScrollPane.NAME, new AdocScrollPaneFactory())
        swingBuilder.registerFactory(JavaScrollPane.NAME, new JavaScrollPaneFactory())

        swingBuilder.registerFactory(AdocTextPane.NAME, new TextArgWidgetFactory(AdocTextPane))
        swingBuilder.registerFactory(JavaTextPane.NAME, new TextArgWidgetFactory(JavaTextPane))
        swingBuilder.registerFactory(TitleButton.NAME, new RichActionWidgetFactory(TitleButton))
        swingBuilder.registerFactory(InputPromptMenuItem.NAME, new RichActionWidgetFactory(InputPromptMenuItem))
    }

}
