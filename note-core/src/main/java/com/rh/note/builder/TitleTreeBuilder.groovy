package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.event.TitleTreeEvent
import com.rh.note.factory.DefaultTreeModelFactory
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import java.awt.*

/**
 * 标题树
 */
@ComponentBean(FrameCategoryEnum.WORK)
class TitleTreeBuilder {

    public static final String tree_id = 'title_tree'
    public static final String model_id = 'title_tree_model'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private TitleTreeEvent event
    @Autowired
    private DefaultTreeModelFactory factory

    @PostConstruct
    void init() {
        def model = {
            swingBuilder.model(id: "${model_id}")
        }

        def titleTree = {
            swingBuilder.tree(id: "${tree_id}",
                    constraints: BorderLayout.EAST,
                    model: model(),
                    mouseClicked: {
                        event.clicked_title_node()
                    },
//                    cellRenderer: new TitleTreeCellRenderer(), // 用于处理数结果的图标
            ) {
            }
        }

        swingBuilder.scrollPane(){
            titleTree()
        }
    }

}
