package com.rh.note.builder

import cn.hutool.core.util.StrUtil
import com.rh.note.annotation.ComponentBean
import com.rh.note.app.config.UserFontConfig
import com.rh.note.common.BaseBuilder
import com.rh.note.component.ReadOnlyTextPane
import com.rh.note.component.TextScrollPane
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.constants.ScopeEnum
import com.rh.note.event.ReadOnlyTextPaneEvent
import com.rh.note.factory.ReadOnlyTextPaneFactory
import com.rh.note.factory.TextScrollPaneFactory
import com.rh.note.path.FileBeanPath
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy
import javax.swing.*
import java.awt.*

/**
 * 只读编辑区
 */
@ComponentBean(frame = FrameCategoryEnum.WORK, scope = ScopeEnum.PROTOTYPE, name = ReadOnlyTextPaneBuilder.text_pane_id)
class ReadOnlyTextPaneBuilder implements BaseBuilder, TextScrollPaneBuilder {

    public static final String text_pane_id = 'java_text_pane_{}'
    @Autowired
    private Font textFont
    @Autowired
    private UserFontConfig fontConfig
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private ReadOnlyTextPaneEvent event
    @Autowired
    private ReadOnlyTextPaneFactory jTextPaneF
    @Autowired
    private TextScrollPaneFactory tScrollPaneF
    private FileBeanPath beanPath

    ReadOnlyTextPaneBuilder(FileBeanPath beanPath) {
        this.beanPath = beanPath
    }

    @PostConstruct
    void init() {
        def textPane = {
            swingBuilder.jTextPane(id: textPaneId(),
                    font: textFont,
                    lineSpacing: fontConfig.getLineSpacing(),
                    background: Color.decode('#2B2B2B'),
                    foreground: Color.decode('#A9B7C6'),
                    beanPath: beanPath,
                    contentChanged: {filePath ->
                        event.refresh_syntax_highlight(filePath)
                    },
            ) {
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

    String textPaneId() {
        return StrUtil.format(text_pane_id, beanPath.getFilePath())
    }

    String scrollPaneId() {
        return StrUtil.format(scroll_pane_id, beanPath.getFilePath())
    }

    TextScrollPane getScrollPane() {
        return swingBuilder."${scrollPaneId()}"
    }

    ReadOnlyTextPane getTextPane() {
        return swingBuilder."${textPaneId()}"
    }

    /**
     * 添加到选项卡面板
     */
    void addToTabbedPane() {
        def tabbedPane = swingBuilder."${TabbedPaneBuilder.id}" as JTabbedPane
        def scrollPane = getScrollPane()
        tabbedPane.add(scrollPane, beanPath.getFileName())
        tabbedPane.setSelectedComponent(scrollPane)
    }
}
