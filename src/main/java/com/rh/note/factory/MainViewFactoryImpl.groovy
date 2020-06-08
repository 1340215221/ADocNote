package com.rh.note.factory


import com.rh.note.model.MainViewBoundEnum
import com.rh.note.util.ISwingBuilder

import java.awt.*
import java.awt.event.AWTEventListener
import java.awt.event.KeyEvent

/**
 * 窗口工厂
 * 用于组合主要控件
 */
class MainViewFactoryImpl implements ISwingBuilder, IMainViewFactory {

    private String path

    MainViewFactoryImpl(String path) {
        this.path = path
    }

    /**
     * 组合控件
     */
    private void build() {
        mainFrame {
            basePanel {
                north { headMenu {} }
                south { bottomSidebar {} }
                west {
                    leftSidebar {
                        leftTitlePanel {
                            fileListTitleButton {}
                        }
                        fileList {}
                    }
                }
                // 打开项目时, 不构建编辑区
                center { editArea {
//                    north{ openFileTitle {} }
//                    west { lineNumSidebar {} }
//                    center { editFileContent {} }
                }}
            }
        }
    }

    /**
     * 注册工厂
     */
    private void registerFactory() {
        swingBuilder.registerFactory('model', new TreeModelFactory())
        swingBuilder.registerFactory('node', new TreeNodeFactory())
    }

    /**
     * 初始化主要控件
     */
    void init() {
        this.keyMapListener()
        MainViewBoundEnum.initAllBound()
        this.registerFactory()
        this.build()
        this.loadConfig()
        this.show()
    }

    /**
     * 全局快捷键
     */
    private void keyMapListener() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.addAWTEventListener(new AWTEventListener() {
            @Override
            void eventDispatched(AWTEvent event) {
                if (event.class != KeyEvent || event.ID != KeyEvent.KEY_PRESSED) {
                    return
                }

                def keyEvent = event as KeyEvent
                if (keyEvent.keyCode == 83 && keyEvent.modifiers == 2) {
                    ActionFactory.action_factory.editAction.saveOperation(path)
                }
            }
        }, AWTEvent.KEY_EVENT_MASK)
    }

    /**
     * 显示主窗口
     */
    private void show() {
        swingBuilder.main_frame.visible = true
    }

    private void loadConfig() {
        // 加载文件列表
        ActionFactory.action_factory.projectListAction.queryProjectList(path)
    }
}