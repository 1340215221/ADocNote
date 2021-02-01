package com.rh.note.action;

import com.rh.note.ao.CreateAdocFileAO;
import com.rh.note.ao.OpenAdocFileByFilePathAO;
import com.rh.note.ao.SaveTextPaneFileByFilePathAO;
import com.rh.note.ao.TextPaneFileWritersAO;
import com.rh.note.api.FileApi;
import com.rh.note.api.FrameContextApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.exception.IsNotSyntaxSugarLineException;
import com.rh.note.line.TitleLine;
import com.rh.note.path.FileBeanPath;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.vo.FindIncludePathInSelectedTextPaneVO;
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

    /**
     * 保存编辑区内容,通过文件路径
     */
    public void saveTextPaneFileByFilePaths(@NonNull SaveTextPaneFileByFilePathAO ao) {
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
        SaveTextPaneFileByFilePathAO ao = new SaveTextPaneFileByFilePathAO(filePaths);
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
        SaveTextPaneFileByFilePathAO ao = new SaveTextPaneFileByFilePathAO(filePaths);
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
        OpenAdocFileByFilePathAO ao = new OpenAdocFileByFilePathAO();
        ao.copy(vo);
        this.openAdocFileByFilePath(ao);
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
        IncludeSyntax syntax = workViewApi.generateIncludeSyntaxByCaretLineOfSelectedTextPane();
        // 创建adoc文件
        CreateAdocFileAO createAdocFileAO = new CreateAdocFileAO();
        createAdocFileAO.copy(syntax);
        fileApi.createAdocFile(createAdocFileAO);
        // 打开新文件, 并初始化内容
        this.openNewFileByFilePath();
        // 保存
        SaveTextPaneFileByFilePathAO saveTextPaneFileByFilePathAO = new SaveTextPaneFileByFilePathAO();
        saveTextPaneFileByFilePathAO.copy(syntax);
        this.saveTextPaneFileByFilePaths(saveTextPaneFileByFilePathAO);
    }

    /**
     * 打开文件, 通过标题节点
     */
    public void openAdocFileByFilePath(@NonNull OpenAdocFileByFilePathAO ao) {
        // 显示已打开文件
        workViewApi.showOpenedFileByFilePath(ao.getFilePath());
        // 打开文件, 加载内容, 并显示
        openNewFileByFilePath(ao.getBeanPath());
    }

    /**
     * 打开文件, 加载内容, 并显示
     */
    private void openNewFileByFilePath(FileBeanPath beanPath) {
        // 判断adoc文件已打开
        if (beanPath == null || StringUtils.isBlank(beanPath.getFilePath()) || workViewApi.checkIsOpenedFile(beanPath.getFilePath())) {
            return;
        }
        // 读取文件内容
        Reader reader = fileApi.readAdocFileContent(beanPath.getAbsolutePath());
        // 生成编辑区
        workViewApi.createAdocTextPane(beanPath, reader);
    }

    /**
     * 关闭窗口
     */
    public void closeContext() {
        frameContextApi.closeContext();
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
}
