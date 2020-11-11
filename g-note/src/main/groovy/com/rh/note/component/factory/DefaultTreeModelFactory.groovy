package com.rh.note.component.factory;

import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Map;

public class DefaultTreeModelFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        return new DefaultTreeModel(new DefaultMutableTreeNode());
    }

    /**
     * 控件名
     */
    public static String name() {
        return "model";
    }
}
