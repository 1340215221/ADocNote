package com.rh.note.factory

import com.rh.note.annotation.ComponentBean
import com.rh.note.component.TextScrollPane
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import groovy.swing.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.*
import java.awt.*

@ComponentBean(FrameCategoryEnum.WORK)
class TextScrollPaneFactory extends BeanFactory {

    @Autowired
    private SwingBuilder swingBuilder

    TextScrollPaneFactory() {
        super(TextScrollPane, false)
    }

    void setChild(FactoryBuilderSupport factory, Object parent, Object child) {
        if (!(parent instanceof JScrollPane) || !(child instanceof Component) || (child instanceof Window)) {
            return
        }
        if (parent.getViewport()?.getView() != null) {
            throw new RuntimeException("ScrollPane can only have one child component")
        }
        if (child instanceof JViewport) {
            parent.setViewport(child)
        } else {
            parent.setViewportView(child)
        }

    }

    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory("tScrollPane", this)
    }
}
