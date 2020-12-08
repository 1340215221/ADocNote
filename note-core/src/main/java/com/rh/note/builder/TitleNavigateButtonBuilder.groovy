package com.rh.note.builder

import com.rh.note.annotation.WorkSingleton
import com.rh.note.base.ITitleBeanPath
import com.rh.note.event.NavigateButtonEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

/**
 * 标题导航栏按钮
 */
@WorkSingleton
class TitleNavigateButtonBuilder {

    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private NavigateButtonEvent event
    private ITitleBeanPath beanPath

    TitleNavigateButtonBuilder(ITitleBeanPath beanPath) {
        this.beanPath = beanPath
    }

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

    static String id(String beanPath) {
        "title_navigate_button_${beanPath}"
    }
}
