package com.rh.note.factory;

import com.rh.note.annotation.SwingBuilderFactory;
import com.rh.note.common.ISwingBuilderFactory;
import com.rh.note.component.TitleTreeNode;
import groovy.swing.SwingBuilder;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * 标题树节点 对象工厂
 */
@SwingBuilderFactory
public class TitleTreeNodeFactory extends AbstractFactory implements ISwingBuilderFactory {

    @Autowired
    private SwingBuilder swingBuilder;

    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        return new TitleTreeNode();
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (!(parent instanceof TitleTreeNode && child instanceof TitleTreeNode)) {
            return;
        }
        ((TitleTreeNode) parent).add((TitleTreeNode) child);
    }

    @Override
    @PostConstruct
    public void registerFactory() {
        swingBuilder.registerFactory("ttNode", this);
    }
}
