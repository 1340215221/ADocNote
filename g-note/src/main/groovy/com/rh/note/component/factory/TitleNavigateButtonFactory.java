package com.rh.note.component.factory;

import com.rh.note.bean.ITitleLine;
import com.rh.note.component.TitleNavigateButton;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

/**
 * 标题导航按钮 对象工厂
 */
public class TitleNavigateButtonFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        TitleNavigateButton button = new TitleNavigateButton();
        return builder;
    }

    /**
     * 控件名
     */
    public static String name() {
        return "tnButton";
    }
}
