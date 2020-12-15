package com.rh.note.factory

import com.rh.note.annotation.SwingBuilderFactory
import com.rh.note.common.ISwingBuilderFactory
import com.rh.note.component.AdocTextPane
import groovy.swing.SwingBuilder
import groovy.swing.factory.TextArgWidgetFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

@SwingBuilderFactory
class AdocTextPaneFactory extends TextArgWidgetFactory implements ISwingBuilderFactory{

    @Autowired
    private SwingBuilder swingBuilder

    AdocTextPaneFactory() {
        super(AdocTextPane)
    }

    @Override
    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory('textPane', this)
    }
}
