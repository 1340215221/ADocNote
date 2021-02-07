package com.rh.note.action;

import com.rh.note.ao.*;
import com.rh.note.api.FileApi;
import com.rh.note.api.FrameContextApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.exception.IsNotSyntaxSugarLineException;
import com.rh.note.line.TitleLine;
import com.rh.note.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.util.Collections;
import java.util.List;

/**
 * 工作窗口 入口
 */
@Slf4j
@Component
public class WorkAction {

    @Autowired
    private WorkViewApi workViewApi;
    @Autowired
    private FrameContextApi frameContextApi;
    @Autowired
    private FileApi fileApi;

    /**
     * 保存所有编辑区内容
     */
    public void saveAllTextPaneFile() {
        List<String> list = workViewApi.getFilePathsOfTextPaneByTabbedPane();
        SaveTextPaneFileByFilePathAO ao = new SaveTextPaneFileByFilePathAO();
        ao.copy(list);
        TextPaneFileWritersAO writerAO = fileApi.getWriterByFilePath(ao);
        workViewApi.saveTextPaneFileByFilePaths(writerAO);
    }

    /**
     * 关闭当前编辑区
     */
    public void closeCurrentTextPane() {
        // 获得要当前被选择的编辑区
        String filePath = workViewApi.getFilePathOfTextPaneSelected();
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        // 保存编辑区内容
        List<String> filePaths = Collections.singletonList(filePath);
        SaveTextPaneFileByFilePathAO ao = new SaveTextPaneFileByFilePathAO();
        ao.copy(filePaths);
        TextPaneFileWritersAO writerAO = fileApi.getWriterByFilePath(ao);
        workViewApi.saveTextPaneFileByFilePaths(writerAO);
        // 关闭编辑区
        workViewApi.closeTextPaneByFilePaths(filePaths);
    }

    /**
     * 关闭没有被选择的编辑区
     */
    public void closeTextPaneNotSelected() {
        // 获得没有被选择的编辑区
        List<String> filePaths = workViewApi.getFilePathsOfTextPaneNotSelected();
        // 保存编辑区内容
        SaveTextPaneFileByFilePathAO ao = new SaveTextPaneFileByFilePathAO();
        ao.copy(filePaths);
        TextPaneFileWritersAO writerAO = fileApi.getWriterByFilePath(ao);
        workViewApi.saveTextPaneFileByFilePaths(writerAO);
        // 关闭编辑区
        workViewApi.closeTextPaneByFilePaths(filePaths);
    }

    /**
     * 打开include行指向的adoc文件, 通过被选择的编辑区
     */
    public void openIncludePointingAdocFileInSelectedTextPane() {
        FindIncludePathInSelectedTextPaneVO vo = workViewApi.getIncludePathInSelectedTextPane();
        if (vo == null) {
            return;
        }
        // 显示已打开文件
        workViewApi.showOpenedFileByFilePath(vo.getFilePath());
        // 打开文件, 加载内容, 并显示
        OpenNewFileByFilePathAO openNewFileByFilePathAO = vo.copyTo();
        openNewFileByFilePath(openNewFileByFilePathAO);
    }

    /**
     * 处理快捷语法, 通过被选择的编辑区光标行内容
     */
    public void handleSyntaxSugarByCaretLineOfSelectedTextPane() {
        if (!workViewApi.checkIsSyntaxSugarByCaretLineOfSelectedTextPane()) {
            throw new IsNotSyntaxSugarLineException();
        }
        workViewApi.generateTitleSyntaxByCaretLineOfSelectedTextPane();
        handleIncludeSyntaxSugarByCaretLineOfSelectedTextPane();
    }

    /**
     * 生成include语句, 通过被选择的编辑区的光标行内容
     */
    private void handleIncludeSyntaxSugarByCaretLineOfSelectedTextPane() {
        // include快捷语法 转 include语句
        GenerateIncludeSyntaxVO syntaxVO = workViewApi.generateIncludeSyntaxByCaretLineOfSelectedTextPane();
        if (syntaxVO == null) {
            return;
        }
        // 创建adoc文件
        fileApi.createAdocFile(syntaxVO.getTargetAbsolutePath());
        // 打开新文件
        OpenNewFileByFilePathAO openNewFileAO = new OpenNewFileByFilePathAO();
        openNewFileAO.copy(syntaxVO);
        this.openNewFileByFilePath(openNewFileAO);
        // 初始化新文件内容
        InitAdocTextPaneContentAO initAdocTextPaneContentAO = new InitAdocTextPaneContentAO();
        initAdocTextPaneContentAO.copy(syntaxVO);
        workViewApi.initOpenedAdocTextPaneContent(initAdocTextPaneContentAO);
        // 保存
        SaveTextPaneFileByFilePathAO saveTextPaneFileByFilePathAO = new SaveTextPaneFileByFilePathAO();
        saveTextPaneFileByFilePathAO.copy(syntaxVO);
        TextPaneFileWritersAO writerAO = fileApi.getWriterByFilePath(saveTextPaneFileByFilePathAO);
        workViewApi.saveTextPaneFileByFilePaths(writerAO);
    }

    /**
     * 打开文件, 通过标题节点
     */
    public void openAdocFileByFilePath() {
        // 获得选择标题节点文件路径
        FindTitleNodeSelectedVO findTitleNodeSelectedVO = workViewApi.getTitleLineBySelectedNode();
        // 显示已打开文件
        workViewApi.showOpenedFileByFilePath(findTitleNodeSelectedVO.getFilePath());
        // 打开文件, 加载内容, 并显示
        OpenNewFileByFilePathAO openNewFileByFilePathAO = findTitleNodeSelectedVO.copyTo();
        openNewFileByFilePath(openNewFileByFilePathAO);
    }

    /**
     * 打开文件, 加载内容, 并显示
     */
    private void openNewFileByFilePath(OpenNewFileByFilePathAO ao) {
        // 判断adoc文件已打开
        if (ao == null || ao.checkMissRequiredParams() || workViewApi.checkIsOpenedFile(ao.getFilePath())) {
            return;
        }
        // 读取文件内容
        Reader reader = fileApi.readAdocFileContent(ao.getAbsolutePath());
        // 生成编辑区
        workViewApi.createAdocTextPane(ao.getBeanPath(), reader);
    }

    /**
     * 刷新被选择编辑区语法高亮
     */
    public void refreshSyntaxHighlightOfTextPaneSelected() {
        workViewApi.refreshSyntaxHighlightOfTextPaneSelected();
    }

    /**
     * 关闭窗口
     */
    public void closeContext() {
        frameContextApi.closeContext();
        LoadContextAO loadContextAO = new LoadContextAO();
        loadContextAO.setFrameCategoryEnum(FrameCategoryEnum.PRO_MANAGE);
        frameContextApi.loadContext(loadContextAO);
    }

    /**
     * 重命名include指向标题名
     */
    public void renameInclude() {
        // 获得被选择文件 vo--filePath
        String filePath = workViewApi.getFilePathOfTextPaneSelected();
        // 获得旧标题名 ao--filePath
        // 请求新标题名 ao--targetFileName
        RequestNewNameOfIncludeOnCaretLineVO requestNewNameVO = workViewApi.requestNewNameOfIncludeOnCaretLine(filePath);
        if (requestNewNameVO == null) {
            return;
        }
        // 保存对应文件内容 ao--filePath
        SaveTextPaneFileByFilePathAO saveTargetFileAO = new SaveTextPaneFileByFilePathAO();
        saveTargetFileAO.copyTarget(requestNewNameVO);
        TextPaneFileWritersAO targetFileWritersAO = fileApi.getWriterByFilePath(saveTargetFileAO);
        workViewApi.saveTextPaneFileByFilePaths(targetFileWritersAO);
        // 关闭对应的textpane ao--filePath
        List<String> targetFilePaths = requestNewNameVO.copyToTargetFilePath();
        workViewApi.closeTextPaneByFilePaths(targetFilePaths);
        // 修改对应文件文件名 -file ao--filePath,newFileName vo--newFilePath
        RenameAdocFileAO renameAdocFileAO = requestNewNameVO.copyToRenameFile();
        RenameAdocFileVO renameAdocFileVO = fileApi.renameAdocFile(renameAdocFileAO);
        if (renameAdocFileVO == null) {
            return;
        }
        // 打开对应textpane ao--newFilePath
        OpenNewFileByFilePathAO openNewFileByFilePathAO = renameAdocFileVO.copyToOpenNewFile();
        this.openNewFileByFilePath(openNewFileByFilePathAO);
        // 修改对应textpane内容中的根标题 ao--newFilePath,newTitleName
        UpdateRootTitleOfTextPaneAO updateRootTitleOfTextPaneAO = renameAdocFileAO.copyToUpdateRootNode();
        workViewApi.updateRootTitleOfTextPane(updateRootTitleOfTextPaneAO);
        // 保存对应文件内容 -file ao--newFilePath
        TextPaneFileWritersAO targetFileWritersAO2 = fileApi.getWriterByFilePath(saveTargetFileAO);
        workViewApi.saveTextPaneFileByFilePaths(targetFileWritersAO2);
        // 修改当前文件include行内容 ao--currentFilePath,newIncludeLineContent
        UpdateCaretLineAO updateCaretLineAO = requestNewNameVO.copyToUpdateCaretLine();
        workViewApi.updateCaretLineContent(updateCaretLineAO);
        // 保存当前文件 -file ao--currentFilePath
        SaveTextPaneFileByFilePathAO saveCurrentFileAO = new SaveTextPaneFileByFilePathAO();
        saveCurrentFileAO.copyCurrent(requestNewNameVO);
        TextPaneFileWritersAO currentFileWritersAO = fileApi.getWriterByFilePath(saveCurrentFileAO);
        workViewApi.saveTextPaneFileByFilePaths(currentFileWritersAO);
    }

    /**
     * 加载标题树根节点
     */
    public void loadRootNode() {
        TitleLine rooTitle = fileApi.readProjectRootTitle();
        if (rooTitle == null) {
            return;
        }
        workViewApi.updateRootNode(rooTitle);
    }

    /**
     * 处理字体风格, 在换行后
     * 不将前一行的语法高亮样式带到下一行
     */
    public void clearFontStyleAfterEnter() {
        workViewApi.clearLinefeedOfFontStyleBeforeCaretLine();
    }
}
