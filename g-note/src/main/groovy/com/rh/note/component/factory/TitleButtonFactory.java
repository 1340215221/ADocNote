package com.rh.note.component.factory;

import com.rh.note.component.TitleButton;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

/**
 * 标题导航按钮 对象工厂
 */
public class TitleButtonFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        return new TitleButton();
    }

    /**
     * 控件名
     */
    public static String name() {
        return "tnButton";
    }
}
