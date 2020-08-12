package com.rh.note.factory.frame

import com.rh.note.action.ProjectListAction
import com.rh.note.build.ActionBuild
import com.rh.note.build.ProjectListFrameBuild
import com.rh.note.util.ISwingBuilder
import com.rh.note.util.common.FrameFactory
import com.rh.note.view.ProjectListFrame

/**
 * 项目列表
 */
class ProjectListFrameFactory implements ISwingBuilder,FrameFactory, ProjectListFrameBuild, ActionBuild {
    @Override
    void init() {
        project_frame {
            project_panel {
                project_list{}
                project_menu{
                    openProjectButton{}
                }
            }
        }
    }

    @Override
    void config() {
        projectListAction.setProjectList()
    }

    @Override
    void show() {
        swingBuilder."${ProjectListFrame.id}".visible = true
    }

    @Override
    void globalSettings() {
    }

    @Override
    void registerFactory() {
    }
}
