package com.rh.note.register;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.util.Map;

public class TreeNodeFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new DefaultMutableTreeNode();
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (!(parent instanceof DefaultMutableTreeNode && child instanceof DefaultMutableTreeNode)) {
            return;
        }
        ((DefaultMutableTreeNode) parent).add((MutableTreeNode) child);
    }
}
