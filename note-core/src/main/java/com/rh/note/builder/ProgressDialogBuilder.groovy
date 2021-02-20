package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseBuilder
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.swing.*
import java.awt.*

/**
 * 进度弹窗 构建者
 */
@ComponentBean(FrameCategoryEnum.UNIVERSAL)
class ProgressDialogBuilder implements BaseBuilder {

    public static final String dialog_id = 'progress_dialog'
    public static final String progress_label_id = 'progress_label'
    public static final String progress_bar_id = 'progress_bar'
    @Autowired
    private SwingBuilder swingBuilder

    @PostConstruct
    void init() {
        def sPanel = {
            swingBuilder.panel(preferredSize: [500, 15],
                    constraints: BorderLayout.SOUTH,
            )
        }

        def label = {
            swingBuilder.label(id: progress_label_id,
                    constraints: BorderLayout.NORTH,
                    text: '  同步项目中:',
                    font: new Font(null, Font.PLAIN, 17),
                    preferredSize: [500, 40],
            )
        }

        def progress = {
            swingBuilder.progressBar(id: progress_bar_id,
                    preferredSize: [500, 10],
                    constraints: BorderLayout.CENTER,
                    value: 0,
            )
        }

        def panel = {
            swingBuilder.panel(layout: new BorderLayout()){
                label()
                progress()
                sPanel()
            }
        }

        swingBuilder.dialog(id: dialog_id,
                title: '自动Git管理',
                pack: true,
        ){
            panel()
        }

        getDialog().locationRelativeTo = null
    }

    JDialog getDialog() {
        return swingBuilder."${dialog_id}"
    }

    JProgressBar getProgressBar() {
        return swingBuilder."${progress_bar_id}"
    }

    JLabel getLabel() {
        return swingBuilder."${progress_label_id}"
    }
}
