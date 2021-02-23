package com.rh.note.builder

import cn.hutool.core.util.StrUtil
import com.rh.note.annotation.ComponentBean
import com.rh.note.app.config.UserFontConfig
import com.rh.note.common.BaseBuilder
import com.rh.note.component.AdocTextPane
import com.rh.note.component.TextScrollPane
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.constants.ScopeEnum
import com.rh.note.event.AdocTextPaneEvent
import com.rh.note.factory.AdocTextPaneFactory
import com.rh.note.factory.TextScrollPaneFactory
import com.rh.note.path.FileBeanPath
import com.rh.note.util.KeymapAction
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.swing.*
import java.awt.*

/**
 * adoc编辑区
 */
@ComponentBean(frame = FrameCategoryEnum.WORK, scope = ScopeEnum.PROTOTYPE, name = AdocTextPaneBuilder.text_pane_id)
class AdocTextPaneBuilder implements BaseBuilder, TextScrollPaneBuilder {

    public static final String text_pane_id = 'adoc_text_pane_{}'
    @Autowired
    private Font textFont
    @Autowired
    private UserFontConfig fontConfig
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private AdocTextPaneEvent event
    @Autowired
    private AdocTextPaneFactory textPaneF
    @Autowired
    private TextScrollPaneFactory tScrollPaneF
    private FileBeanPath beanPath

    AdocTextPaneBuilder(FileBeanPath beanPath) {
        this.beanPath = beanPath
    }

    @PostConstruct
    void init() {
        def textPane = {
            swingBuilder.textPane(id: textPaneId(),
                    font: textFont,
                    lineSpacing: fontConfig.getLineSpacing(),
                    background: Color.decode('#2B2B2B'),
                    foreground: Color.decode('#A9B7C6'),
                    contentChanged: {filePath ->
                        event.refresh_syntax_highlight_by_timer(filePath)
                    },
                    keyPressed: {
                        event.rename_include(it)
                        event.delete_include(it)
//                        event.sink_title(it)
//                        event.inline_title(it)
                    },
                    keyTyped: { // 键入后执行
                    },
                    keyReleased: {
//                        event.open_input_prompt(it)
                    },
                    mouseClicked: {
                        event.enter_include_file(it)
                        event.enter_include_read_only_file(it)
                        event.enter_include_url(it)
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

        addToTabbedPane()
    }

    /**
     * 销毁
     * 1. 从tabbedPane中删除 scrollPane
     * 2. 从swingBuilder中删除控件: textPane scrollPane
     */
    @PreDestroy
    void destroy() {
        // 从选项卡中删除
        def tabbedPane = swingBuilder."${TabbedPaneBuilder.id}" as JTabbedPane
        def scrollPane = getScrollPane()
        tabbedPane.remove(scrollPane)
        // 从swingBuilder中删除
        swingBuilder.variables.remove(textPaneId())
        swingBuilder.variables.remove(scrollPaneId())
    }

    /**
     * 添加keymap
     * 替换已有按键操作
     */
    private void addKeymap() {
        def textPane = swingBuilder."${textPaneId()}" as AdocTextPane
        def keymap = new KeymapAction("textPane", textPane.keymap)
                .addEnterAction { event.enter_input() }
                .addUpAction { event.select_previous_on_prompt(it) }
                .addDownAction { event.select_next_on_prompt(it) }
        textPane.setKeymap(keymap)
    }

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

    /**
     * 添加到选项卡面板
     */
    void addToTabbedPane() {
        def tabbedPane = swingBuilder."${TabbedPaneBuilder.id}" as JTabbedPane
        def scrollPane = getScrollPane()
        tabbedPane.add(scrollPane, beanPath.getFileName())
        event.refresh_syntax_highlight_by_timer(beanPath.getFilePath())
        tabbedPane.setSelectedComponent(scrollPane)
    }
}
