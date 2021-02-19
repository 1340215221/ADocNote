package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseBuilder
import com.rh.note.component.TitleTreeNodeIconRenderer
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.event.TitleTreeEvent
import com.rh.note.factory.DefaultTreeModelFactory
import com.rh.note.util.GitUtil
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.JTree
import javax.swing.tree.DefaultTreeModel
import java.awt.BorderLayout

/**
 * 标题树
 */
@ComponentBean(FrameCategoryEnum.WORK)
class TitleTreeBuilder implements BaseBuilder {

    public static final String tree_id = 'title_tree'
    public static final String model_id = 'title_tree_model'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private TitleTreeEvent event
    @Autowired
    private DefaultTreeModelFactory factory
    @Autowired
    private GitUtil gitUtil

    @PostConstruct
    void init() {
        def model = {
            swingBuilder.model(id: "${model_id}",
            )
        }

        def titleTree = {
            swingBuilder.tree(id: "${tree_id}",
                    constraints: BorderLayout.EAST,
                    model: model(),
                    mouseClicked: {
                        event.clicked_title_node()
                    },
                    cellRenderer: new TitleTreeNodeIconRenderer(), // 用于处理树节点的图标
            ) {
                event.load_root_node()
            }
        }

        swingBuilder.scrollPane(){
            titleTree()
        }
    }

    JTree getTree() {
        return swingBuilder."${tree_id}"
    }

    DefaultTreeModel getModel() {
        return swingBuilder."${model_id}"
    }
}
