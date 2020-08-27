package com.rh.note.builder

import com.rh.note.component.NoteTextPane
import com.rh.note.event.TextAreaEvent
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

    private String filePath

    TextPaneBuilder(String filePath) {
        this.filePath = filePath
    }

    void init() {
        init{}
    }

    @Override
    void init(Closure children) {
        def textPane = {
            swingBuilder.textPane(id: id(filePath),
                    name: id(filePath),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    keyPressed: {
                        TextAreaEvent.rename(it)
                    },
                    filePath: filePath,
            ) {
                addKeymap()
            }
        }

        swingBuilder.scrollPane(id: scrollId(filePath),
                name: scrollId(filePath),
        ){
            textPane()
        }
    }

    /**
     * 添加keymap
     * 替换已有按键操作
     */
    void addKeymap() {
        def textPane = swingBuilder."${id(filePath)}" as NoteTextPane
        def newKeymap = JTextComponent.addKeymap("textPane", textPane.keymap)
        // 添加 回车 事件
        newKeymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), new TextAction('textPane') {
            @Override
            void actionPerformed(ActionEvent e) {
                TextAreaEvent.generateIncludeBlock(e.source.name)
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
