package com.rh.note.factory

import com.rh.note.annotation.ComponentBean
import com.rh.note.component.AdocTextPane
import com.rh.note.component.JavaTextPane
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import groovy.swing.factory.TextArgWidgetFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

/**
 * java编辑区 工厂
 */
@ComponentBean(FrameCategoryEnum.WORK)
class JavaTextPaneFactory extends TextArgWidgetFactory {

    @Autowired
    private SwingBuilder swingBuilder

    JavaTextPaneFactory() {
        super(JavaTextPane)
    }

    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory('jTextPane', this)
    }
}
