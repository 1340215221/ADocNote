package com.rh.note.builder

import cn.hutool.core.util.StrUtil
import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseBuilder
import com.rh.note.component.AdocTextPane
import com.rh.note.component.TextScrollPane
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.constants.ScopeEnum
import com.rh.note.event.AdocTextPaneEvent
import com.rh.note.factory.AdocScrollPaneFactory
import com.rh.note.factory.AdocTextPaneFactory
import com.rh.note.path.FileBeanPath
import com.rh.note.util.KeymapAction
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.JScrollPane
import javax.swing.text.DefaultStyledDocument
import java.awt.*

/**
 * adoc编辑区
 */
@ComponentBean(frame = FrameCategoryEnum.WORK, scope = ScopeEnum.PROTOTYPE, name = AdocTextPaneBuilder.text_pane_id)
class AdocTextPaneBuilder implements BaseBuilder {

    public static final String text_pane_id = 'adoc_text_pane_{}'
    public static final String scroll_pane_id = 'scroll_pane_{}'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private AdocTextPaneEvent event
    @Autowired
    private AdocTextPaneFactory textFactory
    @Autowired
    private AdocScrollPaneFactory scrollFactory
    private FileBeanPath beanPath

    AdocTextPaneBuilder(FileBeanPath beanPath) {
        this.beanPath = beanPath
    }

    @PostConstruct
    void init() {
        def textPane = {
            swingBuilder.textPane(id: textPaneId(),
                    styledDocument: new DefaultStyledDocument(),
                    font: new Font(null, 0, 17),
                    keyPressed: {
//                        event.rename_include(it)
//                        event.sink_title(it)
//                        event.inline_title(it)
//                        event.delete_include(it)
                    },
                    keyReleased: {
//                        event.open_input_prompt(it)
                    },
                    mouseClicked: {
//                        event.enter_include_file(it)
//                        event.enter_include_java_file(it)
                    },
                    caretUpdate: {
//                        event.move_caret()
                    },
                    beanPath: beanPath,
            ) {
                addKeymap()
            }
        }

        swingBuilder.tScrollPane(id: scrollPaneId(),
                beanPath: beanPath,
        ) {
            textPane()
        }
    }

    /**
     * 添加keymap
     * 替换已有按键操作
     */
    void addKeymap() {
        def textPane = swingBuilder."${textPaneId()}" as AdocTextPane
        def keymap = new KeymapAction<AdocTextPane>("textPane", textPane.keymap)
//                .addEnterAction { event.enter_operation(it) }
//                .addUpAction { event.select_previous_on_prompt(it) }
//                .addDownAction { event.select_next_on_prompt(it) }
        textPane.setKeymap(keymap)
    };

    String textPaneId() {
        return StrUtil.format(text_pane_id, beanPath.getFilePath())
    }

    String scrollPaneId() {
        return StrUtil.format(scroll_pane_id, beanPath.getFilePath())
    }

    TextScrollPane getScrollPane() {
        return swingBuilder."${scrollPaneId()}"
    }

    AdocTextPane getTextPane() {
        return swingBuilder."${textPaneId()}"
    }
}
