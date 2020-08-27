package com.rh.note.action;

import com.rh.note.api.FileServiceAPI;
import com.rh.note.api.WorkViewAPI;
import com.rh.note.aspect.DoActionLog;
import com.rh.note.aspect.GlobalExceptionHandler;
import com.rh.note.config.BeanConfig;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.NoteException;
import com.rh.note.file.AdocFile;
import com.rh.note.grammar.TitleGrammar;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 工作区时间触发
 * 用户操作,监听器,定时器触发处
 */
@Setter
@GlobalExceptionHandler
public class WorkAction implements IWorkAction {

    private FileServiceAPI fileServiceAPI = BeanConfig.fileServiceApi;
    private WorkViewAPI workViewAPI = BeanConfig.workViewApi;

    /**
     * 判断当前行是否为include语法
     */
    @DoActionLog("判断当前行是否为include语法")
    public boolean isIncludeGrammarLine(String componentId) {
        return workViewAPI.isIncludeGrammarLine(componentId);
    }

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
     * 修改include文件名字
     */
    @DoActionLog("修改include文件名字")
    public void rename(String componentId) throws Exception {
        if (!workViewAPI.selectIncludeLine(componentId)) {
            return;
        }
        String absolutePath = workViewAPI.getIncludeFilePathByTextPaneId(componentId);
        if (fileServiceAPI.checkFileIsExists(absolutePath)) {
            throw new NoteException(ErrorCodeEnum.FILE_ALREADY_EXIST);
        }
        String newName = workViewAPI.rename(componentId);
        if (StringUtils.isBlank(newName)) {
            return;
        }
        workViewAPI.saveAllEditContent();
        fileServiceAPI.changeRootTitleOfIncludeFile(absolutePath, newName);
        // todo 修改指向文件名, 和文件根标题
        fileServiceAPI.changeFileName(absolutePath, newName);
        // todo 重新打开指向文件的编辑区
        workViewAPI.closeIncludeTargetTextPane(absolutePath);
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
        if (!workViewAPI.selectIncludeGrammar(componentId)) {
            return;
        }
        AdocFile adocFile = workViewAPI.generateIncludeBlock(componentId);
        if (adocFile != null) {
            fileServiceAPI.createFile(adocFile);
        }
        this.saveAllEditContent();
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

    @DoActionLog("插入换行符")
    public void insertEnter(String componentId) throws Exception {
        workViewAPI.insertEnter(componentId);
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
