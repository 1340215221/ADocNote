package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseBuilder
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.event.ProListEvent
import com.rh.note.vo.ProItemVO
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.swing.JList
import java.awt.BorderLayout

/**
 * 项目管理--项目列表
 */
@ComponentBean(FrameCategoryEnum.PRO_MANAGE)
class ProListBuilder implements BaseBuilder {

    public static final String id = 'pro_list'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private ProListEvent event

    void init() {
        swingBuilder.list(id: id,
                fixedCellHeight: 50,
                fixedCellWidth: 300,
                constraints: BorderLayout.WEST,
                mouseClicked: {
                    event.clicked_project_list(it)
                },
                listData: event.loadProListContent()
        ) {
        }
    }

    JList<ProItemVO> getList() {
        return swingBuilder."${id}"
    }
}
