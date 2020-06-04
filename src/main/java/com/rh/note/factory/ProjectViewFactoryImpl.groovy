package com.rh.note.factory

import com.rh.note.model.ProjectViewBoundEnum
import com.rh.note.util.ISwingBuilder
import com.rh.note.vo.RecentlyOpenedRecordVO

import static com.rh.note.factory.ActionFactory.action_factory

/**
 * 项目创建选择窗口
 */
class ProjectViewFactoryImpl implements ISwingBuilder, IProjectViewFactory {

    /**
     * 组合控件
     */
    private void build() {
        project_frame {
            project_panel {
                west { project_list{} }
                east { project_menu{} }
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
        ProjectViewBoundEnum.initAllBound()
        this.registerFactory()
        this.build()
        this.loadConfig()
        this.show()
    }

    /**
     * 显示主窗口
     */
    private void show() {
        swingBuilder.project_frame.visible = true
    }

    /**
     * 加载配置文件
     */
    private void loadConfig() {
        def projectInfos = action_factory.projectManageAction.queryRecentlyOpenedRecords()

        swingBuilder.project_list.listData = projectInfos
    }
}
