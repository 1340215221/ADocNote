package com.rh.note.view

import com.rh.note.event.TitleListEvent
import com.rh.note.util.SwingComponent

import java.awt.*

/**
 * 标题列表
 */
class TitleList implements SwingComponent {
    @Override
    void init(Closure children) {
        def model = {
            swingBuilder.model(id: "${modelId}")
        }

        def fileList = {
            swingBuilder.tree(id: "${treeId}",
                    constraints: BorderLayout.EAST,
                    model: model(),
                    mouseClicked: TitleListEvent.mouseClicked,
            ) {
                children()
            }
        }

        swingBuilder.scrollPane(id: "${id}") {
            fileList()
        }
    }

    static String getId() {
        'title_list'
    }

    static String getTreeId() {
        'title_list_tree'
    }

    static String getModelId() {
        'title_list_model'
    }
}
