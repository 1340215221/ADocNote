package com.rh.note.register;


import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Map;

public class TreeModelFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new DefaultTreeModel(new DefaultMutableTreeNode());
    }
}
