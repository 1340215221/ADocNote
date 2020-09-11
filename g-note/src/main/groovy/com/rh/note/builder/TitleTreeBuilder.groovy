package com.rh.note.builder

import com.rh.note.component.TitleTreeCellRenderer
import com.rh.note.event.TitleTreeEvent
import com.rh.note.util.ISwingBuilder

import java.awt.BorderLayout

/**
 * 工作窗口-标题树
 */
class TitleTreeBuilder implements ISwingBuilder {
    void init(Closure children) {
        def model = {
            swingBuilder.model(id: "${modelId()}")
        }

        def fileList = {
            swingBuilder.tree(id: "${treeId()}",
                    constraints: BorderLayout.EAST,
                    model: model(),
                    mouseClicked: {
                        TitleTreeEvent.clicked_title_node()
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
