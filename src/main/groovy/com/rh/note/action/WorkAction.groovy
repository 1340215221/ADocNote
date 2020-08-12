package com.rh.note.action

import com.rh.note.api.FileAPIService
import com.rh.note.api.WorkViewAPI
import com.rh.note.model.component.WorkFrameImpl
import com.rh.note.model.file.Title
import com.rh.note.util.ISwingBuilder
import org.apache.commons.lang3.StringUtils

/**
 * 工作区时间触发
 * 用户操作,监听器,定时器触发处
 */
class WorkAction implements ISwingBuilder {

    FileAPIService fileAPIService
    WorkViewAPI workViewAPI

    /**
     * 设置项目地址
     */
    void setProjectPath(String projectPath) {
        WorkFrameImpl.absolutePath = projectPath
    }

    /**
     * 打开work_frame
     */
    void openFrame() {
        workViewAPI.openFrame()
    }

    /**
     * 加载文件标题列表
     */
    void showTitleList() {
        Title rootTitle = fileAPIService.findAllTitle()
        workViewAPI.showTitleList(rootTitle)
    }

    /**
     * 打开选择标题
     */
    void openAdocFile() {
        def absolutePath = workViewAPI.showEditingAreaForExistingSelected()
        if (StringUtils.isBlank(absolutePath)) {
            return
        }
        //读取文件内容
        def file = fileAPIService.readTitleFileContent(absolutePath)
        workViewAPI.openNewEditingAreaForSelected(absolutePath, file)
    }

    /**
     * 修改include文件名字
     */
    void rename(String componentId) {
        workViewAPI.rename(componentId)
    }

    /**
     * 显示或隐藏标题列表
     */
    void hiddenOrShowTitleList() {
        workViewAPI.hiddenOrShowTitleList()
    }

    /**
     * 生成include语法块
     */
    void generateIncludeBlock(String componentId) {
        workViewAPI.generateIncludeBlock(componentId)
    }

    /**
     * 保存编辑内容
     */
    void saveAllEditContent() {
        //todo
    }
}
