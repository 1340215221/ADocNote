package com.rh.note.factory.component

import javax.swing.tree.DefaultMutableTreeNode

class TreeNodeFactory extends AbstractFactory {
    @Override
    Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        new DefaultMutableTreeNode()
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (!(parent instanceof DefaultMutableTreeNode && child in DefaultMutableTreeNode)) {
            return
        }

        parent.add(child)
    }
}
