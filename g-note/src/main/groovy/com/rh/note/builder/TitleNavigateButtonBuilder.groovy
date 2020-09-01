package com.rh.note.builder

import com.rh.note.event.TitleListEvent
import com.rh.note.event.TitleNavigateEvent
import com.rh.note.grammar.ITitleGrammar
import com.rh.note.util.SwingComponent

import javax.swing.JButton

/**
 * 标题导航栏按钮
 */
class TitleNavigateButtonBuilder implements SwingComponent {

    private ITitleGrammar titleGrammar

    TitleNavigateButtonBuilder(ITitleGrammar titleGrammar) {
        this.titleGrammar = titleGrammar
    }

    void init() {
        this.init{}
    }

    @Override
    void init(Closure children) {
        swingBuilder.tnButton(id: id(titleGrammar.getName()),
                text: titleGrammar.getName(),
                mouseClicked: TitleNavigateEvent.clicked_title_button,
                titleGrammar: titleGrammar,
                foreground: titleGrammar.getColor(),
        )
    }

    static String id(String titleName) {
        "title_navigate_button_${titleName}"
    }
}
