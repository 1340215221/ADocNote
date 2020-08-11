package com.rh.note.load

import com.rh.note.factory.WorkFrameFactory
import com.rh.note.model.file.Title
import com.rh.note.util.ISwingBuilder
import org.apache.commons.collections4.CollectionUtils

/**
 * 加载工作区参数
 * (所有刷新组件和组件数据的操作)
 * (在运行时, 所有创建组件的操作)
 *
 */
class WorkLoader implements ISwingBuilder {

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
     * 设置项目地址
     */
    void setProjectPath(String projectPath) {
        WorkFrameFactory.projectInfo.setAbsolutePath(projectPath)
    }
}
