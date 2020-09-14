package com.rh.note.component.factory;

import com.rh.note.component.TitleTreeNode;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

/**
 * 标题树节点 对象工厂
 */
public class TitleTreeNodeFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TitleTreeNode node = new TitleTreeNode();
        return node;
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (!(parent instanceof TitleTreeNode && child instanceof TitleTreeNode)) {
            return;
        }
        ((TitleTreeNode) parent).add((TitleTreeNode) child);
    }

    /**
     * 控件名
     */
    public static String name() {
        return "ttNode";
    }
}
