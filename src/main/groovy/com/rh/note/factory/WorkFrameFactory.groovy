package com.rh.note.factory

import com.rh.note.build.ActionBuild
import com.rh.note.build.WorkFrameBuild
import com.rh.note.event.WorkFrameEvent
import com.rh.note.factory.component.TreeModelFactory
import com.rh.note.factory.component.TreeNodeFactory
import com.rh.note.util.ISwingBuilder
import com.rh.note.util.common.FrameFactory
import com.rh.note.view.WorkFrame

import java.awt.*

/**
 * 工作窗口工厂
 */
class WorkFrameFactory implements WorkFrameBuild, ISwingBuilder, ActionBuild, FrameFactory {

    @Override
    void globalSettings() {
        Toolkit toolkit = Toolkit.getDefaultToolkit()
        toolkit.addAWTEventListener(WorkFrameEvent.saveOperation, AWTEvent.KEY_EVENT_MASK)
    }

    /**
     * 初始化数据
     */
    @Override
    void config() {
        workAction.showTitleList()
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
     * 显示窗口
     */
    @Override
    void show() {
        swingBuilder."${WorkFrame.id}".visible = true
    }

    /**
     * 注册组件工厂
     */
    @Override
    void registerFactory() {
        swingBuilder.registerFactory('model', new TreeModelFactory())
        swingBuilder.registerFactory('node', new TreeNodeFactory())
    }

}
