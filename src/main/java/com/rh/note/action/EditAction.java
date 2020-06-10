package com.rh.note.action;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.api.IFileAPIService;
import com.rh.note.entity.adoc.AdocTitile;
import com.rh.note.entity.adoc.impl.AdocProject;
import com.rh.note.factory.ActionFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.util.PrimitiveIterator;

/**
 * 编辑操作
 */
@RequiredArgsConstructor
public class EditAction {

    @NonNull
    private IFileAPIService fileAPIService;
    private EditAreaActionImpl editAreaAction = new EditAreaActionImpl();

    /**
     * include文件重命名
     * shift F6
     */
    public void reNameOfIncludeFile() {
        // 从弹窗中获得新的名字
        // 修改指向的文件名和标题内容
        // 修改include语句
        // 刷新文件列表
    }

    /**
     * 全局Ctrl+S保存
     */
    public void saveOperation(String path) {
        // 保存
        editAreaAction.saveOperation();
        // 重新加载文件列表
        ActionFactory.action_factory.getProjectListAction().queryProjectList(path);
        editAreaAction.refreshFileList();
    }

    /**
     * 重新加载文件列表
     */
    public void refreshFileList(String path) {
        ActionFactory.action_factory.getProjectListAction().queryProjectList(path);
        editAreaAction.refreshFileList();
    }

    /**
     * 打开adoc文件
     */
    public void openAdocFile(AdocTitile nodeName) {
        // 从已打开文档中查找
        if (editAreaAction.findOpenedFile(nodeName.getPath())) {
            // 如果存在就将其放到最上面
            editAreaAction.showOpenedFile(nodeName.getPath());
            return;
        }
        // 构建编辑区域
        editAreaAction.buildEditArea(nodeName);
        // 将文件内容写入到编辑控件中
        File file = fileAPIService.openAdocFile(nodeName.getPath());
        editAreaAction.writeEditArea(nodeName, file);
    }

    /**
     * 创建项目
     */
    public AdocProject createProject(CreateProjectAO ao) {
        return fileAPIService.createProject(ao);
    }

    /**
     * 创建标题
     */

    /**
     * 创建内容
     */

    /**
     * 编辑内容
     */

}
