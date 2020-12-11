package com.rh.note.factory

import com.rh.note.annotation.SwingBuilderFactory
import com.rh.note.common.ISwingBuilderFactory
import com.rh.note.component.AdocScrollPane
import groovy.swing.SwingBuilder
import groovy.swing.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.JScrollPane
import javax.swing.JViewport
import java.awt.Component
import java.awt.Window

@SwingBuilderFactory
class AdocScrollPaneFactory extends BeanFactory implements ISwingBuilderFactory {

    @Autowired
    private SwingBuilder swingBuilder

    AdocScrollPaneFactory() {
        this(AdocScrollPane)
    }

    AdocScrollPaneFactory(Class klass) {
        super(klass, false)
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

    @Override
    @PostConstruct
    void registerFactory() {
        swingBuilder.registerFactory("tScrollPane", this)
    }
}
