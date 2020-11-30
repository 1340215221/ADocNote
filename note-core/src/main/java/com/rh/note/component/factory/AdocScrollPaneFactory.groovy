package com.rh.note.component.factory

import com.rh.note.component.AdocScrollPane
import groovy.swing.factory.BeanFactory

import javax.swing.JScrollPane
import javax.swing.JViewport
import java.awt.Component
import java.awt.Window

class AdocScrollPaneFactory extends BeanFactory {

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
}
