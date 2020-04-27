package com.rh.note.factory

import com.rh.note.model.bound.BoundFactory
import com.rh.note.utils.IInit
import groovy.swing.SwingBuilder

import javax.swing.*

/**
 * 构建窗体结构类
 */
class SwingBuilderFactroy implements IInit {

    public static final SwingBuilder swingBuilder = new SwingBuilder()

    /**
     * 创建主窗口
     */
    void init() {
        this.registerFactory()
        this.assemblyControl()
    }

    /**
     * 注册自定义控件
     */
    private void registerFactory() {
        swingBuilder.registerFactory('mainFrame', new MainFrameFactory())
//        swingBuilder.registerFactory('textPanel', new ComponentFactory(TextPanel))
    }

    /**
     * 组装控件
     */
    private void assemblyControl() {
        def editArea = {
            swingBuilder.textArea(rows: BoundFactory.getEditAreaRow(),
                    columns: BoundFactory.getEditAreaColumn()
            )
        }

        def editPane = {
            swingBuilder.scrollPane(){
                editArea()
            }
        }

        def mainLayout = {
            swingBuilder.borderLayout(center: editPane()
            )
        }

        def bottomPanel = {
            swingBuilder.panel(){
                mainLayout()
            }
        }

        def frame = swingBuilder.frame(title: 'Adoc笔记本',
//        def frame = swingBuilder.mainFrame(title: 'Adoc笔记本',
                bounds: BoundFactory.getMainFrameBound(),
                defaultCloseOperation: WindowConstants.EXIT_ON_CLOSE,
        ) {
            bottomPanel()
        }

        frame.visible = true
    }
}
