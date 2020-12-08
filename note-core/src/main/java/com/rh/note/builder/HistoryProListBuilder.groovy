package com.rh.note.builder

import com.rh.note.annatation.ProjectManage
import com.rh.note.common.DefaultBuilder
import com.rh.note.event.HistoryProjectListEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 项目管理窗口-历史打开项目列表
 */
@ProjectManage
class HistoryProListBuilder implements DefaultBuilder {

    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private HistoryProjectListEvent event

    @Override
    void init(Closure children) {
        swingBuilder.list(id: id(),
                fixedCellHeight:50,
                fixedCellWidth: 300,
                constraints: BorderLayout.WEST,
                mouseClicked: {
                    event.clicked_history_project_list(it)
                },
        ){
            children()
        }
    }

    static String id() {
        'project_list'
    }
}
