package com.rh.note.factory

import com.rh.note.annotation.ComponentBean
import com.rh.note.component.ReadOnlyTextPane
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import groovy.swing.factory.TextArgWidgetFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

/**
 * 只读编辑区 工厂
 */
@ComponentBean(FrameCategoryEnum.WORK)
class ReadOnlyTextPaneFactory extends TextArgWidgetFactory {

    @Autowired
    private SwingBuilder swingBuilder

    ReadOnlyTextPaneFactory() {
        super(ReadOnlyTextPane)
    }

    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory('jTextPane', this)
    }
}
