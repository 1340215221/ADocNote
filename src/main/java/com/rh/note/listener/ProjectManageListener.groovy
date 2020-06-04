package com.rh.note.listener

import com.rh.note.factory.ActionFactory
import com.rh.note.util.ISwingBuilder
import com.rh.note.vo.RecentlyOpenedRecordVO

import javax.swing.*
import java.awt.event.ActionEvent
import java.awt.event.MouseEvent

class ProjectManageListener implements ISwingBuilder {

    static def project_list_mouse_client = { MouseEvent event ->
        if (event.clickCount < 2) {
            return
        }

        def list = event.source as JList
        def value = list.selectedValue as RecentlyOpenedRecordVO

        ActionFactory.action_factory.projectManageAction.openProject(value.projectPath)
    }

    static def file_list_hidden_button = { ActionEvent event ->
        swingBuilder.file_list_panel.visible = !swingBuilder.file_list_panel.visible
        swingBuilder.base_panel.validate()
        swingBuilder.base_panel.repaint()
    }

}
