package com.rh.note.component.factory;

import com.rh.note.component.AdocScrollPane;
import com.rh.note.component.AdocTextPane;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.awt.Component;
import java.util.Map;

/**
 * 编辑区滚动面板
 */
public class AdocScrollPaneFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new AdocScrollPane();
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (!(parent instanceof AdocScrollPane) || !(child instanceof AdocTextPane)) {
            return;
        }

        ((AdocScrollPane) parent).getViewport().add((Component) child);
    }

    public static String name() {
        return "tScrollPane";
    }
}
