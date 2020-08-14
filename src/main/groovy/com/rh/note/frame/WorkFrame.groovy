package com.rh.note.frame


import com.rh.note.common.IFrame
import com.rh.note.event.WorkFrameEvent
import com.rh.note.register.TextAreaFactory
import com.rh.note.register.TreeModelFactory
import com.rh.note.register.TreeNodeFactory
import com.rh.note.util.ISwingBuilder

import java.awt.*

/**
 * 工作窗口工厂
 */
class WorkFrame implements IWorkFrame, IFrame, ISwingBuilder {

    @Override
    void globalSettings() {
        Toolkit toolkit = Toolkit.getDefaultToolkit()
        toolkit.addAWTEventListener(WorkFrameEvent.saveOperation, AWTEvent.KEY_EVENT_MASK)
    }

    /**
     * 组装组建
     */
    @Override
    void init() {
        workFrame {
            basePanel {
                headMenu {}
                bottomSidebar {}
                leftSidebar {
                    leftTitlePanel {
                        fileListTitleButton {}
                    }
                    titleList {}
                }
                editArea {
                }
            }
        }
    }

    /**
     * 注册组件工厂
     */
    @Override
    void registerComponent() {
        swingBuilder.registerFactory('model', new TreeModelFactory())
        swingBuilder.registerFactory('node', new TreeNodeFactory())
        swingBuilder.registerFactory('textArea', new TextAreaFactory())
    }

}
