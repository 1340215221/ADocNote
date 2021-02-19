package com.rh.note


import com.formdev.flatlaf.FlatIntelliJLaf
import com.rh.note.constants.PromptMessageEnum
import com.rh.note.view.ErrorMsgDialogView
import groovy.swing.SwingBuilder

import javax.swing.*
import javax.swing.text.SimpleAttributeSet
import javax.swing.text.StyleConstants
import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeModel
import java.awt.*

class TestDemo {
    static {
        FlatIntelliJLaf.install()
    }
    static def builder = new SwingBuilder()

    static main(args) {


        ErrorMsgDialogView errorMsgDialogView = new ErrorMsgDialogView().init(PromptMessageEnum.GIT_OPERATION_FAILED);
        errorMsgDialogView.showConfirmDialog();
        println "11111"
    }
}
