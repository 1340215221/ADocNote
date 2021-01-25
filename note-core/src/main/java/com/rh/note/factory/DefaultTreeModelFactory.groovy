package com.rh.note.factory

import com.rh.note.annotation.ComponentBean
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel

@ComponentBean(FrameCategoryEnum.WORK)
class DefaultTreeModelFactory extends AbstractFactory {

    @Autowired
    private SwingBuilder swingBuilder

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        return new DefaultTreeModel(new DefaultMutableTreeNode())
    }

    @PostConstruct
    void register() {
        swingBuilder.registerFactory('model', this)
    }
}
