package com.rh.note.register;

import com.rh.note.component.NoteButton;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

/**
 * 标题导航按钮工厂
 */
public class TitleNavigateButtonFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new NoteButton();
    }
}
