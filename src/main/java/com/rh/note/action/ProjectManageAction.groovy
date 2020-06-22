package com.rh.note.action

import com.rh.note.factory.MainViewFactoryImpl
import com.rh.note.util.ISwingBuilder
import com.rh.note.vo.RecentlyOpenedRecordVO

import javax.swing.JFrame

/**
 * 项目管理
 */
class ProjectManageAction implements ISwingBuilder {

    /**
     * 打开项目
     */
    void openProject(String projectPath) {
        // 关闭项目选择界面
        def frame = swingBuilder.project_frame as JFrame
        frame.dispose()
        // 打开项目编辑界面
        new MainViewFactoryImpl(projectPath).init()
    }

    /**
     * 获取最近打开的项目记录
     */
    RecentlyOpenedRecordVO[] queryRecentlyOpenedRecords() {
        [
                new RecentlyOpenedRecordVO(projectName: 'Java笔记', projectPath: '/home/hang/Documents/InterviewNote'),
        ]
    }

}
