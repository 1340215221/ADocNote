package com.rh.note.view

import com.rh.note.factory.ActionFactory

import javax.swing.*
import javax.swing.tree.TreePath
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
                        /*
                        def tree = swingBuilder.file_list_tree as JTree
                        def location = tree.getPathForLocation(e.x, e.y)
                        if (location == null) {
                            return
                        }

                        def model1 = swingBuilder.file_list_model as DefaultTreeModel
                        model1.insertNodeInto(new DefaultMutableTreeNode("name"), location.lastPathComponent, 0)
                         */
                        if (e.clickCount < 2) {
                            return
                        }

                        def tree = swingBuilder.file_list_tree as JTree
                        def selectPath = tree.selectionPath
                        if (selectPath == null) {
                            return
                        }

                        ActionFactory.action_factory.editAction.openAdocFile(selectPath.lastPathComponent.userObject)
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
