package com.rh.note

import groovy.swing.SwingBuilder

import javax.imageio.ImageIO
import javax.swing.*
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeCellRenderer
import javax.swing.tree.DefaultTreeModel
import java.awt.*

class TestApplication {
    public static void main(String[] args) {
//        def swingBuilder = new SwingBuilder()
//        swingBuilder.frame(id: 'frame',
//                size: [900, 500],
//                defaultCloseOperation: JFrame.EXIT_ON_CLOSE,
//        ) {
//            swingBuilder.panel(layout:  new BorderLayout()) {
//                def tree = swingBuilder.tree(id: 'tree') {
//                    def model = new DefaultTreeModel(new DefaultMutableTreeNode(userObject: '水果'))
//                    def rootNode = model.getRoot() as DefaultMutableTreeNode
//                    rootNode.add(new DefaultMutableTreeNode(userObject: '苹果'))
//                    rootNode.add(new DefaultMutableTreeNode(userObject: '香蕉'))
//                }
//                def icon = new ImageIcon('icon/file/folder@2x.png')
//                tree.setCellRenderer(new DefaultTreeCellRenderer(
//                        closedIcon: icon,
//                        openIcon: icon,
//                        leafIcon: icon
//                ))
//            }
//        }.visible = true

        def renderer = new DefaultTreeCellRenderer()
        def icon = renderer.getDefaultClosedIcon()
        println icon.getIconHeight()
        println icon.getIconWidth()
    }
}
