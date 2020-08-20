package com.rh.note.action;

import com.rh.note.api.FileServiceAPI;
import com.rh.note.api.WorkViewAPI;
import com.rh.note.aspect.DoActionLog;
import com.rh.note.aspect.GlobalExceptionHandler;
import com.rh.note.config.BeanConfig;
import com.rh.note.file.AdocFile;
import com.rh.note.grammar.IncludeGrammar;
import com.rh.note.grammar.TitleGrammar;
import lombok.Setter;

import java.io.File;

/**
 * 工作区时间触发
 * 用户操作,监听器,定时器触发处
 */
@Setter
@GlobalExceptionHandler
public class WorkAction {

    private FileServiceAPI fileServiceAPI = BeanConfig.fileServiceApi;
    private WorkViewAPI workViewAPI = BeanConfig.workViewApi;

    /**
     * 打开work_frame
     */
    @DoActionLog("打开work_frame")
    public void openFrame() {
        TitleGrammar rootTitle = fileServiceAPI.findAllTitle();
        workViewAPI.initFrame();
        workViewAPI.loadTitleList(rootTitle);
        workViewAPI.showMainFrame();
    }

    /**
     * 加载文件标题列表
     */
    @DoActionLog("加载文件标题列表")
    public void loadTitleList() {
        TitleGrammar rootTitle = fileServiceAPI.findAllTitle();
        workViewAPI.loadTitleList(rootTitle);
    }

    /**
     * 打开选择标题
     */
    @DoActionLog("打开选择标题")
    public void openAdocFile() {
        TitleGrammar titleGrammar = workViewAPI.showEditingAreaForExistingSelected();
        if (titleGrammar == null) {
            return;
        }
        //读取文件内容
        File file = fileServiceAPI.readTitleFileContent(titleGrammar.getAbsolutePath());
        workViewAPI.openNewEditingAreaForSelected(titleGrammar.getFilePath(), file);
    }

    /**
     * 打开选择标题
     */
    @DoActionLog("打开选择标题")
    public void openAdocFile2() {
        TitleGrammar titleGrammar = workViewAPI.showEditingAreaForExistingSelected2();
        if (titleGrammar == null) {
            return;
        }
        //读取文件内容
        File file = fileServiceAPI.readTitleFileContent(titleGrammar.getAbsolutePath());
        workViewAPI.openNewEditingAreaForSelected2(titleGrammar.getFilePath(), file);
    }

    /**
     * 修改include文件名字
     */
    @DoActionLog("修改include文件名字")
    public void rename(String componentId) throws Exception {
        workViewAPI.rename(componentId);
    }

    /**
     * 修改include文件名字
     */
    @DoActionLog("修改include文件名字")
    public void rename2(String componentId) throws Exception {
        workViewAPI.rename2(componentId);
    }

    /**
     * 显示或隐藏标题列表
     */
    @DoActionLog("显示或隐藏标题列表")
    public void hiddenOrShowTitleList() {
        workViewAPI.hiddenOrShowTitleList();
    }

    /**
     * 生成include语法块
     */
    @DoActionLog("生成include语法块")
    public void generateIncludeBlock(String componentId) throws Exception {
        IncludeGrammar includeGrammar = workViewAPI.matchIncludeForTextLine(componentId);
        if (includeGrammar == null) {
            return;
        }
        AdocFile adocFile = workViewAPI.generateIncludeBlock(componentId);
        if (adocFile != null) {
            fileServiceAPI.createFile(adocFile);
        }
        this.saveAllEditContent();
    }

    /**
     * 生成include语法块
     */
    @DoActionLog("生成include语法块")
    public void generateIncludeBlock2(String componentId) throws Exception {
        IncludeGrammar includeGrammar = workViewAPI.matchIncludeForTextLine(componentId);
        if (includeGrammar == null) {
            return;
        }
        AdocFile adocFile = workViewAPI.generateIncludeBlock2(componentId);
        if (adocFile != null) {
            fileServiceAPI.createFile(adocFile);
        }
        this.saveAllEditContent2();
    }

    /**
     * 保存编辑内容
     */
    @DoActionLog("保存编辑内容")
    public void saveAllEditContent() {
        workViewAPI.saveAllEditContent().forEach(filePath ->
                fileServiceAPI.openFileOutputStream(filePath));
        this.loadTitleList();
    }

    /**
     * 保存编辑内容
     */
    @DoActionLog("保存编辑内容")
    public void saveAllEditContent2() {
        workViewAPI.saveAllEditContent2().forEach(filePath ->
                fileServiceAPI.openFileOutputStream(filePath));
        this.loadTitleList();
    }

    /**
     * 扫描初始化项目结构
     */
    @DoActionLog("扫描初始化项目结构")
    public void initProjectStructure() {
        // 初始化config文件
        fileServiceAPI.initConfigFile();
        // 初始化readme文件
        fileServiceAPI.initReadMeFile();
    }
}
