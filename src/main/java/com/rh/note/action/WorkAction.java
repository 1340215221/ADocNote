package com.rh.note.action;

import com.rh.note.api.FileAPIService;
import com.rh.note.api.WorkViewAPI;
import com.rh.note.view.WorkFrameView;
import com.rh.note.file.AdocFile;
import com.rh.note.file.ConfigFile;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.util.ISwingBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 工作区时间触发
 * 用户操作,监听器,定时器触发处
 */
@RequiredArgsConstructor
class WorkAction implements ISwingBuilder {

    @NonNull
    private FileAPIService fileAPIService;
    @NonNull
    private WorkViewAPI workViewAPI;

    /**
     * 设置项目地址
     */
    public void setProjectPath(String projectPath) {
        WorkFrameView.setAbsolutePath(projectPath);
    }

    /**
     * 打开work_frame
     */
    public void openFrame() {
        workViewAPI.openFrame();
    }

    /**
     * 加载文件标题列表
     */
    public void loadTitleList() {
        TitleGrammar rootTitle = fileAPIService.findAllTitle();
        workViewAPI.showTitleList(rootTitle);
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
        File file = fileAPIService.readTitleFileContent(absolutePath);
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
        ConfigFile config = fileAPIService.findConfigFile();
        AdocFile adocFile = workViewAPI.generateIncludeBlock(componentId, config);
        if (adocFile != null) {
            fileAPIService.createFile(adocFile);
        }
        loadTitleList();
    }

    /**
     * 保存编辑内容
     */
    public void saveAllEditContent() {
        //todo
    }

    /**
     * 扫描初始化项目结构
     */
    public void initProjectStructure() {
        // 初始化config文件
        fileAPIService.initConfigFile();
        // 初始化readme文件
        fileAPIService.initReadMeFile();
    }
}
