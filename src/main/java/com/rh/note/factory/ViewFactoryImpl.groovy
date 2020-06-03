package com.rh.note.factory

import com.rh.note.model.BoundEnum
import com.rh.note.util.ISwingBuilder

/**
 * 窗口工厂
 * 用于组合主要控件
 */
class ViewFactoryImpl implements ISwingBuilder, IViewFactory {

    /**
     * 组合控件
     */
    private void build() {
        mainFrame {
            basePanel {
                north { headMenu {} }
                south { bottomSidebar {} }
                west { leftSidebar {} }
                center { editArea {
                    north{ openFileTitle {} }
                    west { lineNumSidebar {} }
                    center { editFileContent {} }
                }}
            }
        }
    }

    /**
     * 注册工厂
     */
    private void registerFactory() {
    }

    /**
     * 初始化主要控件
     */
    void init() {
        BoundEnum.initAllBound()
        this.registerFactory()
        this.build()
        this.show()
    }

    /**
     * 显示主窗口
     */
    private void show() {
        swingBuilder.main_frame.visible = true
    }

}
