package com.rh.note.builder

import com.rh.note.annotation.ProManageSingleton
import com.rh.note.common.ISingletonStaticBuilder
import com.rh.note.event.HistoryProjectListEvent
import com.rh.note.vo.RecentlyOpenedRecordVO
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import javax.swing.JList
import java.awt.BorderLayout

/**
 * 项目管理窗口-历史打开项目列表
 */
@ProManageSingleton(HistoryProListBuilder.builder_name)
class HistoryProListBuilder implements ISingletonStaticBuilder {

    static final String builder_name = "project_list"
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private HistoryProjectListEvent event

    @Override
    void init(Closure children) {
        swingBuilder.list(id: id(),
                fixedCellHeight: 50,
                fixedCellWidth: 300,
                constraints: BorderLayout.WEST,
                mouseClicked: {
                    event.clicked_history_project_list(it)
                },
        ) {
            children()
        }
    }

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
    }

    JList<RecentlyOpenedRecordVO> getProList() {
        return swingBuilder."${id()}"
    }

    static String id() {
        'project_list'
    }
}
