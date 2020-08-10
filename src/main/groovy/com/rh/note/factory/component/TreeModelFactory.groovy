package com.rh.note.factory.component


import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

class TreeModelFactory extends AbstractFactory {
    @Override
    Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        new DefaultTreeModel(new DefaultMutableTreeNode())
    }
}
