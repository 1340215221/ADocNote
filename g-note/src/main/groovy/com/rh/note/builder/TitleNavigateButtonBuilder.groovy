package com.rh.note.builder


import com.rh.note.base.ISwingBuilder
import com.rh.note.base.ITitleBeanPath
import com.rh.note.event.NavigateButtonEvent

/**
 * 标题导航栏按钮
 */
class TitleNavigateButtonBuilder implements ISwingBuilder {

    private ITitleBeanPath beanPath

    TitleNavigateButtonBuilder(ITitleBeanPath beanPath) {
        this.beanPath = beanPath
    }

    void init() {
        swingBuilder.tnButton(id: id(beanPath.getBeanPath()),
                text: beanPath.getTitleName(),
                mouseClicked: {
                    NavigateButtonEvent.clicked_navigate_button(it)
                },
                beanPath: beanPath,
                foreground: beanPath.getColor(),
        )
    }

    static String id(String titleName) {
        "title_navigate_button_${titleName}"
    }
}
