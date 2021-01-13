package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 项目管理--菜单面板
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
class ProMenuPanelBuilder {

    public static final String id = 'pro_menu_panel'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private ImportProButtonBuilder importProButtonBuilder

    void init() {
        swingBuilder.panel(id: id,
                constraints: BorderLayout.CENTER,
        ) {
            importProButtonBuilder.init()
        }
    }
}
