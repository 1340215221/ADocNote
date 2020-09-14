package com.rh.note.action;

/**
 * 项目管理入口
 */
public interface IProjectManagementAction {
    /**
     * 打开被选择的最近的项目
     */
    void openSelectedHistoryProject(String projectPath);

    /**
     * 打开选择项目弹窗
     */
    void openDialogForSelectProject();

    /**
     * 打开项目,通过目录路径
     */
    void openProjectByDirectoryPath(String filePath);
}
