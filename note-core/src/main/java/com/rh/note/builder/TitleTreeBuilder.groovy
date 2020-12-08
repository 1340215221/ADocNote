package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.DefaultBuilder
import com.rh.note.component.TitleTreeCellRenderer
import com.rh.note.event.TitleTreeEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import java.awt.BorderLayout

/**
 * 工作窗口-标题树
 */
@WorkSingleton
class TitleTreeBuilder implements DefaultBuilder {

    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private TitleTreeEvent event

    @Override
    void init(Closure children) {
        def model = {
            swingBuilder.model(id: "${modelId()}")
        }

        def fileList = {
            swingBuilder.tree(id: "${treeId()}",
                    constraints: BorderLayout.EAST,
                    model: model(),
                    mouseClicked: {
                        event.clicked_title_node()
                    },
                    cellRenderer: new TitleTreeCellRenderer(),
            ) {
                children()
            }
        }

        swingBuilder.scrollPane(id: "${id()}") {
            fileList()
        }
    }

    static String id() {
        'title_list'
    }

    static String treeId() {
        'title_list_tree'
    }

    static String modelId() {
        'title_list_model'
    }
}
