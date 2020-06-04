package com.rh.note.view

import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import java.awt.event.MouseEvent

class FileList implements SwingBuilderImpl {
    @Override
    def init(Closure run) {
        def model = {
            swingBuilder.model(id: "${id}_model")
        }

        def fileList = {
            swingBuilder.tree(id: "${id}_tree",
                    model: model(),
                    mouseClicked: { MouseEvent e ->
                        println 'clicked'
                        def tree = swingBuilder.file_list_tree as JTree
                        def root = tree.model.root as DefaultMutableTreeNode
                        def node = swingBuilder.node(userObject: 'hah')
                        root.add(node)
                    }
            ) {
                run()
            }
        }

        swingBuilder.scrollPane(id: "${id}_panel",) {
            fileList()
        }
    }

    @Override
    String getId() {
        return 'file_list'
    }
}
