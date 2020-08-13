package com.rh.note.action;

import com.rh.note.api.FileServiceAPI;
import com.rh.note.api.WorkViewAPI;
import com.rh.note.file.AdocFile;
import com.rh.note.file.ConfigFile;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.view.WorkFrameRunView;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 工作区时间触发
 * 用户操作,监听器,定时器触发处
 */
@Setter
public class WorkAction {

    private FileServiceAPI fileServiceAPI;
    private WorkViewAPI workViewAPI;

    /**
     * 设置项目地址
     */
    public void setProjectPath(String projectPath) {
        WorkFrameRunView.setAbsolutePath(projectPath);
    }

    /**
     * 打开work_frame
     */
    public void openFrame() {
        TitleGrammar rootTitle = fileServiceAPI.findAllTitle();
        workViewAPI.initFrame();
        workViewAPI.loadTitleList(rootTitle);
        workViewAPI.showMainFrame();
    }

    /**
     * 加载文件标题列表
     */
    public void loadTitleList() {
        TitleGrammar rootTitle = fileServiceAPI.findAllTitle();
        workViewAPI.loadTitleList(rootTitle);
    }

    /**
     * 打开选择标题
     */
    public void openAdocFile() {
        String absolutePath = workViewAPI.showEditingAreaForExistingSelected();
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        //读取文件内容
        File file = fileServiceAPI.readTitleFileContent(absolutePath);
        workViewAPI.openNewEditingAreaForSelected(absolutePath, file);
    }

    /**
     * 修改include文件名字
     */
    public void rename(String componentId) throws Exception {
        workViewAPI.rename(componentId);
    }

    /**
     * 显示或隐藏标题列表
     */
    public void hiddenOrShowTitleList() {
        workViewAPI.hiddenOrShowTitleList();
    }

    /**
     * 生成include语法块
     */
    public void generateIncludeBlock(String componentId) throws Exception {
        ConfigFile config = fileServiceAPI.findConfigFile();
        AdocFile adocFile = workViewAPI.generateIncludeBlock(componentId, config);
        if (adocFile != null) {
            fileServiceAPI.createFile(adocFile);
        }
        loadTitleList();
    }

    /**
     * 保存编辑内容
     */
    public void saveAllEditContent() {
        //todo
        //遍历编辑区
        //获取编辑区内容，和文件地址
        //将内容保存写入到文件中
    }

    /**
     * 扫描初始化项目结构
     */
    public void initProjectStructure() {
        // 初始化config文件
        fileServiceAPI.initConfigFile();
        // 初始化readme文件
        fileServiceAPI.initReadMeFile();
    }
}
