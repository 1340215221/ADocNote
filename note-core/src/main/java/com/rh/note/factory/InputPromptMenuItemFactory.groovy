package com.rh.note.factory

import com.rh.note.annotation.SwingBuilderFactory
import com.rh.note.common.ISwingBuilderFactory
import groovy.swing.SwingBuilder
import groovy.swing.factory.RichActionWidgetFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

@SwingBuilderFactory
class InputPromptMenuItemFactory extends RichActionWidgetFactory implements ISwingBuilderFactory {

    @Autowired
    private SwingBuilder swingBuilder

    InputPromptMenuItemFactory(Class klass) {
        super(klass)
    }

    @Override
    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory('menuItem', this)
    }
}
