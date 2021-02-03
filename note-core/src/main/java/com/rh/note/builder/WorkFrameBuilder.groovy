package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseBuilder
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.event.WorkFrameEvent
import com.rh.note.util.GlobalKeymapUtil
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.swing.JFrame
import javax.swing.WindowConstants
import java.awt.BorderLayout

/**
 * 工作窗口 构建者
 */
@ComponentBean(FrameCategoryEnum.WORK)
class WorkFrameBuilder implements BaseBuilder {

    public static final String id = 'work_frame'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private WorkFrameEvent event
    @Autowired
    private TitleNavigatePanelBuilder navigateButtonPanelBuilder
    @Autowired
    private TabbedPaneBuilder tabbedPaneBuilder
    @Autowired
    private LeftSidebarMenuPanelBuilder leftSidebarMenuPanelBuilder
    @Autowired
    private TitleTreeBuilder titleTreeBuilder

    @PostConstruct
    void init() {
        def center_panel = {
            swingBuilder.panel(constraints: BorderLayout.CENTER,
                    layout: new BorderLayout(),
            ) {
                navigateButtonPanelBuilder.init()
                tabbedPaneBuilder.init()
            }
        }

        def left_panel = {
            swingBuilder.panel(constraints: BorderLayout.WEST,
                    layout: new BorderLayout(),
            ) {
                leftSidebarMenuPanelBuilder.init()
                titleTreeBuilder.init()
            }
        }

        def panel = {
            swingBuilder.panel(layout: new BorderLayout(),
                    preferredSize: [900, 600],
            ) {
                center_panel()
                left_panel()
            }
        }

        swingBuilder.frame(id: id,
                title: 'adoc笔记',
                pack: true,
                defaultCloseOperation: WindowConstants.DO_NOTHING_ON_CLOSE,
                windowClosing: {
                    // 主线程不能阻塞, 阻塞时无法渲染页面
                    swingBuilder.doOutside {
                        event.save_all_text_pane()
                        event.close_frame()
                    }
                }
        ) {
            panel()
            globalKeymap()
        }
        windowCentered()
        showFrame()
    }

    /**
     * 窗口居中
     * 设置窗口居中. 需要放在添加完子控件, pack通过子控件计算完窗口大小后
     */
    private void windowCentered() {
        swingBuilder."${id}".locationRelativeTo = null
    }

    /**
     * 显示窗口
     */
    private void showFrame() {
        swingBuilder."${id}".visible = true
    }

    @PreDestroy
    void destroy() {
        swingBuilder."${id}".dispose()
    }

    JFrame getFrame() {
        return swingBuilder."${id}"
    }

    /**
     * 全局快捷键
     */
    void globalKeymap() {
        def keymap = new GlobalKeymapUtil()
        // ctrl + s 保存
        keymap.event_ctrl_S {
            event.save_all_edit_text()
        }
        // alt + q 关闭当前文件
        keymap.event_alt_Q {
            event.closeCurrentTextPane()
        }
        // alt + w 关闭其它文件
        keymap.event_alt_W {
            event.closeOtherTextPane()
        }
    }
}
