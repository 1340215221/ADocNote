package com.rh.note.action;

import com.rh.note.ao.*;
import com.rh.note.api.FileApi;
import com.rh.note.api.FrameContextApi;
import com.rh.note.api.GitApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.common.IShowProgress;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.exception.IsNotSyntaxSugarLineException;
import com.rh.note.line.TitleLine;
import com.rh.note.util.CurrentAdocProConfigUtil;
import com.rh.note.vo.*;
import lombok.NonNull;
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
    @Autowired
    private GitApi gitApi;

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
     * 打开include行指向的文件, 通过被选择的编辑区
     */
    public void openIncludePointingFileInSelectedTextPane(@NonNull OpenIncludePointingFileBaseAO ao) {
        FindIncludePathInSelectedTextPaneVO vo = workViewApi.getIncludePathInSelectedTextPane();
        ao.setVo(vo);
        if (ao.checkParamError()) {
            return;
        }
        // 显示已打开文件
        workViewApi.showOpenedFileByFilePath(ao.getTargetFilePath());
        // 打开文件, 加载内容, 并显示
        OpenNewFileByFilePathBaseAO openNewFileAO = ao.copyTo();
        openNewFileByFilePath(openNewFileAO);
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
        OpenNewFileByFilePathBaseAO openNewFileAO = new OpenNewAdocFileByFilePathAO();
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
        OpenNewFileByFilePathBaseAO openNewFileByFilePathAO = findTitleNodeSelectedVO.copyTo();
        openNewFileByFilePath(openNewFileByFilePathAO);
    }

    /**
     * 打开文件, 加载内容, 并显示
     */
    private void openNewFileByFilePath(OpenNewFileByFilePathBaseAO ao) {
        // 判断adoc文件已打开
        if (ao == null || ao.checkMissRequiredParams() || workViewApi.checkIsOpenedFile(ao.getFilePath())) {
            return;
        }
        if (ao instanceof OpenNewAdocFileByFilePathAO) {
            // 读取文件内容
            Reader reader = fileApi.getFileReader(ao.getAbsolutePath());
            // 生成编辑区
            workViewApi.createAdocTextPane(ao.getBeanPath(), reader);
        }
        if (ao instanceof OpenNewReadOnlyFileByFilePathAO) {
            // 读取文件内容
            Reader reader = fileApi.getFileReader(ao.getAbsolutePath());
            // 生成编辑区
            workViewApi.createJavaTextPane(ao.getBeanPath(), reader);
        }
    }

    /**
     * 刷新指定adoc编辑区语法高亮
     */
    public void refreshSyntaxHighlightOfAdocTextPaneByFilePath(String filePath) {
        workViewApi.refreshSyntaxHighlightOfAdocTextPaneByFilePath(filePath);
    }

    /**
     * 刷新指定java编辑区语法高亮
     */
    public void refreshSyntaxHighlightOfJavaTextPaneByFilePath(String filePath) {
        workViewApi.refreshSyntaxHighlightOfJavaTextPaneByFilePath(filePath);
    }

    /**
     * 刷新被选择编辑区语法高亮
     */
    public void refreshSyntaxHighlightOfAdocTextPaneSelected() {
        workViewApi.refreshSyntaxHighlightOfAdocTextPaneSelected();
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
     * 安全删除include
     */
    public void deleteIncludeOnCaretLineOfTextPaneSelected() {
        // 弹窗确认
        ConfirmDeleteIncludeVO confirmDeleteIncludeVO = workViewApi.confirmDeleteIncludeOnCaretLineOfTextPaneSelected();
        if (confirmDeleteIncludeVO == null) {
            return;
        }
        // 关闭指向textpane ao--targetFilePath
        List<String> targetFilePaths = confirmDeleteIncludeVO.copyToTargetFilePath();
        workViewApi.closeTextPaneByFilePaths(targetFilePaths);
        // 删除指向文件 ao--targetAbsolutePath
        DeleteAdocFileAO deleteAdocFileAO = confirmDeleteIncludeVO.copyToDeleteAdocFile();
        fileApi.deleteAdocFile(deleteAdocFileAO);
        // 删除当前行 ao--filePath
        workViewApi.deleteCaretLine(confirmDeleteIncludeVO.getFilePath());
        // 保存当前文件 ao--filePath
        SaveTextPaneFileByFilePathAO saveTextPaneFileAO = new SaveTextPaneFileByFilePathAO();
        saveTextPaneFileAO.copy(confirmDeleteIncludeVO);
        TextPaneFileWritersAO textPaneFileWritersAO = fileApi.getWriterByFilePath(saveTextPaneFileAO);
        workViewApi.saveTextPaneFileByFilePaths(textPaneFileWritersAO);
    }

    /**
     * 重命名include指向标题名
     */
    public void renameIncludeOnCaretLineOfTextPaneSelected() {
        // 获得被选择文件 vo--filePath
        String filePath = workViewApi.getFilePathOfTextPaneSelected();
        // 获得旧标题名 ao--filePath
        // 请求新标题名 ao--targetFileName
        RequestNewNameOfIncludeOnCaretLineVO requestNewNameVO = workViewApi.requestNewNameOfIncludeOnCaretLine(filePath);
        if (requestNewNameVO == null) {
            return;
        }
        SaveTextPaneFileByFilePathAO saveTargetFileAO = new SaveTextPaneFileByFilePathAO();
        saveTargetFileAO.copyTarget(requestNewNameVO);
        if (workViewApi.checkIsOpenedFile(requestNewNameVO.getTargetFilePath())) {
            // todo 现在这种两步， 先获得文件流， 再获得控件，方式非常危险。 获得流时就已经把文件内容清空了。 应该在前面先进行判断
            // 保存对应文件内容 ao--filePath
            TextPaneFileWritersAO targetFileWritersAO = fileApi.getWriterByFilePath(saveTargetFileAO);
            workViewApi.saveTextPaneFileByFilePaths(targetFileWritersAO);
            // 关闭对应的textpane ao--filePath
            List<String> targetFilePaths = requestNewNameVO.copyToTargetFilePath();
            workViewApi.closeTextPaneByFilePaths(targetFilePaths);
        }
        // 修改对应文件文件名 -file ao--filePath,newFileName vo--newFilePath
        RenameAdocFileAO renameAdocFileAO = requestNewNameVO.copyToRenameFile();
        boolean renameFile = fileApi.renameAdocFile(renameAdocFileAO);
        if (!renameFile) {
            return;
        }
        // 打开对应textpane ao--newFilePath
        OpenNewFileByFilePathBaseAO openNewFileByFilePathAO = requestNewNameVO.copyToOpenNewFile();
        this.openNewFileByFilePath(openNewFileByFilePathAO);
        // 修改对应textpane内容中的根标题 ao--newFilePath,newTitleName
        UpdateRootTitleOfTextPaneAO updateRootTitleOfTextPaneAO = requestNewNameVO.copyToUpdateRootNode();
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
        this.loadRootNode();
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
     * 同步到远程
     */
    public void gitPush() {
        String proPath = CurrentAdocProConfigUtil.getProPath();
        while (true) {
            IShowProgress showProgress = workViewApi.openProgressDialog();
            GitPushAO gitPushAO = new GitPushAO();
            gitPushAO.setAbsolutePath(proPath);
            gitPushAO.copy(showProgress);
            try {
                gitApi.push(gitPushAO);
                break;
            } catch (Exception e) {
                log.error("[项目关闭时, 同步项目内容] error", e);
                boolean isOk = workViewApi.promptGitOperationFailed();
                if (!isOk) {
                    log.info("[项目关闭时, 取消同步项目内容]");
                    return;
                }
            } finally {
                workViewApi.closeProgressDialog();
            }
        }
    }
}
