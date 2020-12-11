package com.rh.note.factory

import com.rh.note.annotation.SwingBuilderFactory
import com.rh.note.common.ISwingBuilderFactory
import groovy.swing.SwingBuilder
import groovy.swing.factory.TextArgWidgetFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

@SwingBuilderFactory
class JavaTextPaneFactory extends TextArgWidgetFactory implements ISwingBuilderFactory{

    @Autowired
    private SwingBuilder swingBuilder

    JavaTextPaneFactory(Class klass) {
        super(klass)
    }

    @Override
    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory('jTextPane', this)
    }
}
