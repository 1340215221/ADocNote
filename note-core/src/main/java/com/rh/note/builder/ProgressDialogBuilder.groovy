package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseBuilder
import com.rh.note.constants.FrameCategoryEnum
import groovy.swing.SwingBuilder
import org.springframework.beans.factory.annotation.Autowired

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

    void init() {
        def label = {
            swingBuilder.label(id: progress_label_id,
                    text: '同步项目中',
            )
        }

        def progress = {
            swingBuilder.progressBar(id: progress_bar_id,
                    preferredSize: [500, 10],
                    value: 0,
            )
        }

        def panel = {
            swingBuilder.panel(layout: new BorderLayout()){
                label()
                progress()
            }
        }

        swingBuilder.dialog(id: dialog_id,
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
