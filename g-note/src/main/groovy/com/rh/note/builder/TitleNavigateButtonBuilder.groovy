package com.rh.note.builder

import com.rh.note.bean.ITitleLine
import com.rh.note.event.NavigateButtonEvent
import com.rh.note.util.ISwingBuilder

/**
 * 标题导航栏按钮
 */
class TitleNavigateButtonBuilder implements ISwingBuilder {

    private ITitleLine titleLine

    TitleNavigateButtonBuilder(ITitleLine titleLine) {
        this.titleLine = titleLine
    }

    void init() {
        swingBuilder.tnButton(id: id(titleLine.getTitleName()), //todo 这个id是真的需要吗,标题名当id存在重复情况
                text: titleLine.getTitleName(),
                mouseClicked: {
                    NavigateButtonEvent.clicked_navigate_button(it)
                },
                titleLine: titleLine,
                foreground: titleLine.getColor(),
        )
    }

    static String id(String titleName) {
        "title_navigate_button_${titleName}"
    }
}
