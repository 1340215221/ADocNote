package com.rh.note.builder

import com.rh.note.event.HistoryProjectListEvent
import com.rh.note.util.ISwingBuilder

import java.awt.BorderLayout

/**
 * 项目管理窗口-历史打开项目列表
 */
class HistoryProjectListBuilder implements ISwingBuilder {
    void init(Closure children) {
        swingBuilder.list(id: id(),
                fixedCellHeight:50,
                fixedCellWidth: 300,
                constraints: BorderLayout.WEST,
                mouseClicked: {
                    HistoryProjectListEvent.clicked_history_project_list()
                },
        ){
            children()
        }
    }

    static String id() {
        'project_list'
    }
}
