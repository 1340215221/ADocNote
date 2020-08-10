package com.rh.note.load

import com.rh.note.factory.WorkFrameFactory
import com.rh.note.model.file.Title
import com.rh.note.util.ISwingBuilder
import com.rh.note.view.BasePanel
import com.rh.note.view.TitleList
import org.apache.commons.collections4.CollectionUtils

import javax.swing.*

/**
 * 加载工作区参数
 * (所有刷新组件和组件数据的操作)
 * (在运行时, 所有创建组件的操作)
 *
 */
class WorkLoader implements ISwingBuilder {

    /**
     * 显示或隐藏标题列表
     */
    void hiddenOrShowTitleList() {
        swingBuilder."${TitleList.id}".visible = !swingBuilder."${TitleList.id}"
        swingBuilder."${BasePanel.id}".validate()
        swingBuilder."${BasePanel.id}".repaint()
    }

    /**
     * 设置节点树
     */
    void setTitleTree(Title title) {
        if (title == null) {
            return
        }
        def root = swingBuilder.node(userObject: title){
            this.setChildrenNode(title)
        }
        swingBuilder."${TitleList.modelId}".root = root
    }

    /**
     * 递归组装子节点
     */
    private void setChildrenNode(Title title) {
        if (title == null || CollectionUtils.isEmpty(title.getChildrenTitle())) {
            return
        }

        title.getChildrenTitle().each {
            swingBuilder.node(userObject: it){
                this.setChildrenNode(it)
            }
        }
    }

    /**
     * 将所有节点展开
     */
    void expandAllNode() {
        def tree = swingBuilder."${TitleList.treeId}" as JTree
        for (int i = 1; i <= tree.rowCount; i++) {
            tree.expandRow(i)
        }
    }

    /**
     * 打开work_frame
     */
    void openFrame() {
        new WorkFrameFactory().start()
    }

    /**
     * 设置项目地址
     */
    void setProjectPath(String projectPath) {
        WorkFrameFactory.projectInfo.setAbsolutePath(projectPath)
    }
}
