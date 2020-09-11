package com.rh.note.component.factory;

import com.rh.note.bean.ITitleLine;
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
        if (attributes.get("titleGrammar") instanceof ITitleLine) {
            node.setTitleLine((ITitleLine) attributes.get("titleGrammar"));
        }
        return node;
    }

    /**
     * 控件名
     */
    public static String name() {
        return "ttNode";
    }
}
