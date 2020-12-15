package com.rh.note.builder

import com.rh.note.annotation.WorkPrototype
import com.rh.note.base.ITitleBeanPath
import com.rh.note.common.IPrototypeBuilder
import com.rh.note.component.TitleButton
import com.rh.note.event.NavigateButtonEvent
import com.rh.note.factory.TitleButtonFactory
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.swing.JPanel
import java.awt.Component

/**
 * 标题导航栏按钮
 */
@WorkPrototype(TitleNavigateButtonBuilder.builder_name)
class TitleNavigateButtonBuilder implements IPrototypeBuilder {

    static final String builder_name = "title_navigate_button_{}"
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private NavigateButtonEvent event
    @Autowired
    private TitleButtonFactory tbFactory
    private ITitleBeanPath beanPath

    TitleNavigateButtonBuilder(ITitleBeanPath beanPath) {
        this.beanPath = beanPath
    }

    @Override
    @PostConstruct
    void init() {
        swingBuilder.tnButton(id: id(beanPath.getBeanPath()),
                text: beanPath.getTitleName(),
                mouseClicked: {
                    event.clicked_navigate_button(it)
                },
                beanPath: beanPath,
                foreground: beanPath.getColor(),
        )
    }

    @Override
    @PreDestroy
    void destroy() {
        def panel = swingBuilder."${TabbedPaneBuilder.navigateId()}" as JPanel
        def component = swingBuilder.variables."${id(beanPath.getBeanPath())}" as Component
        panel.remove(component)
        swingBuilder.variables.remove(id(beanPath.getBeanPath()))
    }

    @Override
    String getInstanceName() {
        return id(beanPath.getBeanPath())
    }

    static String id(String beanPath) {
        "title_navigate_button_${beanPath}"
    }

    TitleButton getNavigateButton() {
        return swingBuilder."${id(beanPath.getBeanPath())}"
    }
}
