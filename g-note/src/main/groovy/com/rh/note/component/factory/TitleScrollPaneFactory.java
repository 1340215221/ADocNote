package com.rh.note.component.factory;

import com.rh.note.component.AdocTextPane;
import com.rh.note.component.TitleScrollPane;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.awt.*;
import java.util.Map;

/**
 * 编辑区滚动面板
 */
public class TitleScrollPaneFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new TitleScrollPane();
    }

    @Override
    public void setChild(FactoryBuilderSupport builder, Object parent, Object child) {
        if (!(parent instanceof TitleScrollPane) || !(child instanceof AdocTextPane)) {
            return;
        }

        ((TitleScrollPane) parent).getViewport().add((Component) child);
    }

    public static String name() {
        return "tScrollPane";
    }
}
