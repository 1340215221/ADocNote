package com.rh.note.factory

import com.rh.note.annotation.SwingBuilderFactory
import com.rh.note.common.ISwingBuilderFactory
import com.rh.note.component.JavaScrollPane
import groovy.swing.SwingBuilder
import groovy.swing.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.JScrollPane
import javax.swing.JViewport
import java.awt.Component
import java.awt.Window

/**
 * java文件编辑区滚动控件
 */
@SwingBuilderFactory
class JavaScrollPaneFactory extends BeanFactory implements ISwingBuilderFactory {

    @Autowired
    private SwingBuilder swingBuilder

    JavaScrollPaneFactory() {
        this(JavaScrollPane)
    }

    JavaScrollPaneFactory(Class klass) {
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
        swingBuilder.registerFactory("jScrollPane", this)
    }
}
