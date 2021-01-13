package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.*
import java.awt.*

/**
 * 项目管理窗口
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
class ProManageBuilder {

    public static final String frame_id = 'pro_manage_frame'
    public static final String panel_id = 'pro_manage_panel'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private ProMenuPanelBuilder panelBuilder
    @Autowired
    private ProListBuilder proListBuilder

    @PostConstruct
    void init() {
        // 面板
        def panel = {
            swingBuilder.list(id: panel_id,
                    fixedCellHeight: 50,
                    fixedCellWidth: 300,
                    constraints: BorderLayout.WEST,
                    mouseClicked: {
                        panelBuilder.init()
                        proListBuilder.init()
                    },
            ) {
            }
        }
        // 窗口
        swingBuilder.frame(id: frame_id,
                pack: true,
                resizable: false,
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
                title: '打开项目',
        ) {
            panel()
        }
        // 设置窗口位置
        windowCentered()
    }

    /**
     * 窗口居中
     * 设置窗口居中. 需要放在添加完子控件, pack通过子控件计算完窗口大小后
     */
    private void windowCentered() {
        swingBuilder."${frame_id}".locationRelativeTo = null
    }

}
