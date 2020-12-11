package com.rh.note.factory

import com.rh.note.annotation.SwingBuilderFactory
import com.rh.note.common.ISwingBuilderFactory
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

@SwingBuilderFactory
class DefaultTreeModelFactory extends AbstractFactory implements ISwingBuilderFactory {

    @Autowired
    private SwingBuilder swingBuilder

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        return new DefaultTreeModel(new DefaultMutableTreeNode());
    }

    @Override
    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory('model', this)
    }
}
