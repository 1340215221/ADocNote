package com.rh.note.factory;

import com.rh.note.view.MainFrame;
import groovy.swing.factory.FrameFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

public class MainFrameFactory extends FrameFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport factoryBuilderSupport, Object o, Object o1, Map map) throws InstantiationException, IllegalAccessException {
        return new MainFrame();
    }
}
