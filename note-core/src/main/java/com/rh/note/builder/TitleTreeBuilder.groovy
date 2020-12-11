package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.common.ISingletonStaticBuilder
import com.rh.note.component.TitleTreeCellRenderer
import com.rh.note.event.TitleTreeEvent
import com.rh.note.factory.DefaultTreeModelFactory
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PreDestroy
import javax.swing.JTree
import javax.swing.tree.DefaultTreeModel
import java.awt.BorderLayout

/**
 * 工作窗口-标题树
 */
@WorkSingleton(TitleTreeBuilder.builder_name)
class TitleTreeBuilder implements ISingletonStaticBuilder {

    static final String builder_name = "title_list"
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private TitleTreeEvent event
    @Autowired
    private DefaultTreeModelFactory dtmFactory

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

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
        swingBuilder.variables.remove(treeId())
        swingBuilder.variables.remove(modelId())
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

    DefaultTreeModel getModel() {
        return swingBuilder."${modelId()}"
    }

    JTree getTree() {
        return swingBuilder."${treeId()}"
    }
}
