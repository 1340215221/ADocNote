package com.rh.note.builder

import com.rh.note.annotation.WorkPrototype
import com.rh.note.annotation.WorkSingleton
import com.rh.note.base.BeanPath
import com.rh.note.common.IPrototypeBuilder
import com.rh.note.common.ISingletonDynamicBuilder
import com.rh.note.component.AdocTextPane
import com.rh.note.event.TextPaneEvent
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
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
@WorkPrototype(builder_name)
class TextPaneBuilder implements IPrototypeBuilder {

    static final String builder_name = "text_pane_{}"
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private TextPaneEvent event
    private BeanPath beanPath

    TextPaneBuilder(BeanPath beanPath) {
        this.beanPath = beanPath
    }

    @Override
    @PostConstruct
    void init() {
        def textPane = {
            swingBuilder.textPane(id: id(beanPath.getBeanPath()),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    keyPressed: {
                        event.rename_include(it)
                        event.sink_title(it)
                        event.inline_title(it)
                        event.delete_include(it)
                    },
                    keyReleased: {
                        event.open_input_prompt(it)
                    },
                    mouseClicked: {
                        event.enter_include_file(it)
                        event.enter_include_java_file(it)
                    },
                    caretUpdate: {
                        event.move_caret()
                    },
                    beanPath: beanPath,
            ) {
                addKeymap()
            }
        }

        swingBuilder.tScrollPane(id: scrollId(beanPath.getBeanPath()),
                beanPath: beanPath,
        ){
            textPane()
        }
    }

    /**
     * 添加keymap
     * 替换已有按键操作
     */
    void addKeymap() {
        def textPane = swingBuilder."${id(beanPath.getBeanPath())}" as AdocTextPane
        def newKeymap = JTextComponent.addKeymap("textPane", textPane.keymap)
        // 添加 回车 事件
        newKeymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), new TextAction('textPane') {
            @Override
            void actionPerformed(ActionEvent e) {
                event.enter_operation(e)
            }
        })
        // 添加 上键 事件
        newKeymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), new TextAction('menu_up') {
            @Override
            void actionPerformed(ActionEvent e) {
                event.select_previous_on_prompt(e)
            }
        })
        // 添加 下键 事件
        newKeymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), new TextAction('menu_down') {
            @Override
            void actionPerformed(ActionEvent e) {
                event.select_next_on_prompt(e)
            }
        })
        textPane.setKeymap(newKeymap)
    };

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id(beanPath.getBeanPath()))
        swingBuilder.variables.remove(scrollId(beanPath.getBeanPath()))
    }

    @Override
    String getInstanceName() {
        return id(beanPath.getBeanPath())
    }

    static String id(String filePath) {
        return "text_pane_${filePath}"
    }

    static String scrollId(String filePath) {
        return "text_pane_scroll_${filePath}"
    }
}
