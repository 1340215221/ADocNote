package com.rh.note.event

import com.rh.note.action.IWorkAction
import com.rh.note.component.NoteButton
import com.rh.note.config.ActionConfig

import java.awt.event.MouseEvent

/**
 * 标题导航时间
 */
class TitleNavigateEvent {

    private static IWorkAction workAction = ActionConfig.get.workAction()

    /**
     * 点击标题导航中的标题
     */
    static def clicked_title_button = { MouseEvent event ->
        def button = event.source as NoteButton
        workAction.openTitleByNavigate(button.getTitleGrammar())
    }

}
