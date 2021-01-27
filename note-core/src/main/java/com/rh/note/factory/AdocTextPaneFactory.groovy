package com.rh.note.factory

import com.rh.note.annotation.ComponentBean
import com.rh.note.component.AdocTextPane
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import groovy.swing.factory.TextArgWidgetFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

@ComponentBean(FrameCategoryEnum.WORK)
class AdocTextPaneFactory extends TextArgWidgetFactory {

    @Autowired
    private SwingBuilder swingBuilder

    AdocTextPaneFactory() {
        super(AdocTextPane)
    }

    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory('textPane', this)
    }
}
