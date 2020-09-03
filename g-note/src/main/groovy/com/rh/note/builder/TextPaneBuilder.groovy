package com.rh.note.builder

import com.rh.note.component.NoteTextPane
import com.rh.note.event.TextAreaEvent
import com.rh.note.grammar.ITitleGrammar
import com.rh.note.util.SwingComponent

import javax.swing.*
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.JTextComponent
import javax.swing.text.TextAction
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

/**
 * 编辑区
 */
class TextPaneBuilder implements SwingComponent {

    private ITitleGrammar titleGrammar

    TextPaneBuilder(ITitleGrammar titleGrammar) {
        this.titleGrammar = titleGrammar
    }

    void init() {
        init{}
    }

    @Override
    void init(Closure children) {
        def textPane = {
            swingBuilder.textPane(id: id(titleGrammar.getFilePath()),
                    name: id(titleGrammar.getFilePath()),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    keyPressed: {
                        TextAreaEvent.rename(it)
                        TextAreaEvent.sinkTitle(it)
                    },
                    mouseClicked: {
                        TextAreaEvent.enterInclude(it)
                    },
                    titleGrammar: titleGrammar,
            ) {
                addKeymap()
            }
        }

        swingBuilder.scrollPane(id: scrollId(titleGrammar.getFilePath()),
                name: scrollId(titleGrammar.getFilePath()),
        ){
            textPane()
        }
    }

    /**
     * 添加keymap
     * 替换已有按键操作
     */
    void addKeymap() {
        def textPane = swingBuilder."${id(titleGrammar.getFilePath())}" as NoteTextPane
        def newKeymap = JTextComponent.addKeymap("textPane", textPane.keymap)
        // 添加 回车 事件
        newKeymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), new TextAction('textPane') {
            @Override
            void actionPerformed(ActionEvent e) {
                TextAreaEvent.enter(e)
            }
        })
        // 添加 ctrl + del 事件
        newKeymap.addActionForKeyStroke(KeyStroke.getKeyStroke(127, 2), new TextAction('textPane') {
            @Override
            void actionPerformed(ActionEvent e) {
                TextAreaEvent.deleteInclude(e)
            }
        })
        textPane.setKeymap(newKeymap)
    };

    static String id(String filePath) {
        return "text_pane_${filePath}"
    }

    static String scrollId(String filePath) {
        return "text_pane_scroll_${filePath}"
    }
}
