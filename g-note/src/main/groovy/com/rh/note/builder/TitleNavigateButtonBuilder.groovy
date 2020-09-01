package com.rh.note.builder

import com.rh.note.grammar.ITitleGrammar
import com.rh.note.util.SwingComponent

/**
 * 标题导航栏按钮
 */
class TitleNavigateButtonBuilder implements SwingComponent {

    private ITitleGrammar titleGrammar

    TitleNavigateButtonBuilder(ITitleGrammar titleGrammar) {
        this.titleGrammar = titleGrammar
    }

    @Override
    void init(Closure children) {
        swingBuilder.button(id: id,
                text: titleGrammar.
        )
    }

    static String getId() {
        'title_navigate_button'
    }
}
