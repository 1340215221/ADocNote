package com.rh.note.builder

import com.rh.note.bean.IAdocFile
import com.rh.note.component.AdocTextPane
import com.rh.note.event.TextPaneEvent
import com.rh.note.util.ISwingBuilder

import javax.swing.KeyStroke
import javax.swing.text.DefaultStyledDocument
import javax.swing.text.JTextComponent
import javax.swing.text.TextAction
import java.awt.Font
import java.awt.event.ActionEvent
import java.awt.event.KeyEvent

/**
 * 工作窗口-编辑区
 */
class TextPaneBuilder implements ISwingBuilder {

    private IAdocFile adocFile

    TextPaneBuilder(IAdocFile adocFile) {
        this.adocFile = adocFile
    }

    void init() {
        def textPane = {
            swingBuilder.textPane(id: id(adocFile.getFilePath()),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    keyPressed: {
                        TextPaneEvent.rename(it)
                        TextPaneEvent.sink_title(it)
                    },
                    mouseClicked: {
                        TextPaneEvent.enter_include_file(it)
                    },
                    adocFile: adocFile,
            ) {
                addKeymap()
            }
        }

        swingBuilder.tScrollPane(id: scrollId(adocFile.getFilePath()),
                adocFile: adocFile,
        ){
            textPane()
        }
    }

    /**
     * 添加keymap
     * 替换已有按键操作
     */
    void addKeymap() {
        def textPane = swingBuilder."${id(adocFile.getFilePath())}" as AdocTextPane
        def newKeymap = JTextComponent.addKeymap("textPane", textPane.keymap)
        // 添加 回车 事件
        newKeymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), new TextAction('textPane') {
            @Override
            void actionPerformed(ActionEvent e) {
                TextPaneEvent.enter_operation(e)
            }
        })
        // 添加 ctrl + del 事件
        newKeymap.addActionForKeyStroke(KeyStroke.getKeyStroke(127, 2), new TextAction('textPane') {
            @Override
            void actionPerformed(ActionEvent e) {
                TextPaneEvent.delete_include(e)
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
