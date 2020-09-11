package com.rh.note.component.factory;

import com.rh.note.bean.IAdocFile;
import com.rh.note.component.AdocTextPane;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

/**
 * 编辑区 对象工厂
 */
public class AdocTextPaneFactory extends AbstractFactory {
    @Override
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        AdocTextPane textPane = new AdocTextPane();
        if (attributes.get("adocFile") instanceof IAdocFile) {
            textPane.setAdocFile((IAdocFile) attributes.get("adocFile"));
        }
        return textPane;
    }

    /**
     * 控件名
     */
    public static String name() {
        return "textPane";
    }
}
