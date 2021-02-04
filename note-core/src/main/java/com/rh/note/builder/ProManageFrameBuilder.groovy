package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.event.ProManageEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.swing.*
import java.awt.*

/**
 * 项目管理窗口
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
class ProManageFrameBuilder {

    public static final String id = 'pro_manage_frame'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private ProMenuPanelBuilder panelBuilder
    @Autowired
    private ProListBuilder proListBuilder
    @Autowired
    private ProManageEvent event

    @PostConstruct
    void init() {
        // 面板
        def panel = {
            swingBuilder.panel(preferredSize: [800, 500],
                    layout: new BorderLayout(),
            ){
                panelBuilder.init()
                proListBuilder.init()
            }
        }
        // 窗口
        swingBuilder.frame(id: id,
                pack: true,
                resizable: false,
                defaultCloseOperation: WindowConstants.DO_NOTHING_ON_CLOSE,
                title: '打开项目',
                windowClosing: {
                    swingBuilder.doOutside {
                        event.exit_app()
                    }
                }
        ) {
            panel()
        }
        // 设置窗口位置
        windowCentered()
        // 显示窗口
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
}
