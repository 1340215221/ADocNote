package com.rh.note.factory

import com.rh.note.entity.adoc.AdocTitile
import com.rh.note.model.MainViewBoundEnum
import com.rh.note.util.ISwingBuilder
import org.apache.commons.collections4.CollectionUtils
import org.apache.commons.lang3.StringUtils

import javax.swing.tree.DefaultMutableTreeNode
import javax.swing.tree.DefaultTreeSelectionModel
import javax.swing.tree.TreePath

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
//                center { editArea {
//                    north{ openFileTitle {} }
//                    west { lineNumSidebar {} }
//                    center { editFileContent {} }
//                }}
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
        MainViewBoundEnum.initAllBound()
        this.registerFactory()
        this.build()
        this.loadConfig()
        this.show()
    }

    /**
     * 显示主窗口
     */
    private void show() {
        swingBuilder.main_frame.visible = true
    }

    private void loadConfig() {
        // 加载文件列表
        def list = ActionFactory.action_factory.projectListAction.queryProjectList(path)

        def node = swingBuilder.node(userObject: this.getProjectName()){
            this.buildNode(list)
        }

        swingBuilder.file_list_model.root = node
    }

    /**
     * 构建节点
     */
    private void buildNode(List<AdocTitile> list) {
        if (CollectionUtils.isEmpty(list)) {
            return
        }

        list.each {AdocTitile titile ->
            swingBuilder.node(userObject: titile.displayName) {
                this.buildNode(titile.childrenTitle)
            }
        }

    }

    /**
     * 获得项目名字
     */
    private String getProjectName() {
        if (StringUtils.isBlank(path)) {
            return ''
        }

        def split = path.split(this.getFileSeparatorAsRegex())
        if (split == null || split.length < 1) {
            return ''
        }

        def lastStr = split[split.length - 1]
        if (StringUtils.isNotBlank(lastStr)) {
            return lastStr
        }

        if (split.length > 1) {
            return split[split.length - 2]
        }

        return ''
    }

    /**
     * 获得文件分隔符的正则字符串
     */
    private String getFileSeparatorAsRegex() {
        if ('\\' == File.separator) {
            return '\\\\'
        }

        return File.separator
    }
}