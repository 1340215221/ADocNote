package com.rh.note.factory

import com.rh.note.annotation.ComponentBean
import com.rh.note.component.TitleTreeNode
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

/**
 * 标题树节点 对象工厂
 */
@ComponentBean(FrameCategoryEnum.WORK)
class TitleTreeNodeFactory extends AbstractFactory {

    @Autowired
    private SwingBuilder swingBuilder;

    @Override
    Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        return new TitleTreeNode()
    }

    @Override
    void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (!(parent instanceof TitleTreeNode && child instanceof TitleTreeNode)) {
            return
        }
        parent.add(child)
    }

    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory("ttNode", this)
    }
}
