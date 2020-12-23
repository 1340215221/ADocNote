package com.rh.note.action.impl;

import com.rh.note.action.IWorkAction;
import com.rh.note.ao.GenerateIncludeSyntaxAO;
import com.rh.note.ao.GenerateJavaIncludeSyntaxAO;
import com.rh.note.ao.GenerateTitleSyntaxAO;
import com.rh.note.ao.ICreateFileAndInitContentAO;
import com.rh.note.ao.ITitleContentAO;
import com.rh.note.ao.IncludeFilePathInfoAO;
import com.rh.note.ao.IncludePromptAO;
import com.rh.note.ao.InlineTitleAO;
import com.rh.note.ao.MarkLineAO;
import com.rh.note.ao.MatchIncludeInfoBySelectedTextAO;
import com.rh.note.ao.MatchTitleInfoBySelectedTextAO;
import com.rh.note.ao.RenameIncludeAO;
import com.rh.note.ao.SelectPromptItemAO;
import com.rh.note.ao.TargetFilePathByIncludeJavaLineAO;
import com.rh.note.ao.TitleContentAO;
import com.rh.note.api.FileServiceApi;
import com.rh.note.api.GitServiceApi;
import com.rh.note.api.WorkLoaderApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.component.InputPromptMenuItem;
import com.rh.note.constants.PromptMessageEnum;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.util.DefaultEditorKitUtil;
import com.rh.note.view.ShowMessageDialogView;
import com.rh.note.view.TextPaneView;
import com.rh.note.vo.ITitleLineVO;
import com.rh.note.vo.WriterVO;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 工作窗口 入口
 */
@Slf4j
@Component
public class WorkAction implements IWorkAction {

    @Autowired
    private WorkViewApi workViewApi;
    @Autowired
    private FileServiceApi fileServiceApi;
    @Autowired
    private GitServiceApi gitServiceApi;
    @Autowired
    private WorkLoaderApi workLoaderApi;

    /**
     * 提交adoc内容
     * git add和commit
     */
    public void commit() {
        gitServiceApi.commit(null);
        workViewApi.promptPopup(PromptMessageEnum.GIT_SUBMITTED_SUCCESSFULLY);
    }

    /**
     * git push
     */
    public void push() {
        gitServiceApi.push();
    }

    /**
     * 启动窗口
     */
    public void initFrame() {
        TitleLine rootTitle = fileServiceApi.readAllTitleByProjectPath();
        if (rootTitle == null) {
            throw new ApplicationException(ErrorCodeEnum.CANNOT_OPEN_A_PROJECT_WITHOUT_A_TITLE);
        }
        workLoaderApi.loadContext();
        workLoaderApi.loadComponent();
        workViewApi.loadTitleTree(rootTitle);
    }

    public void loadTitleTree() {
        TitleLine rootTitle = fileServiceApi.readAllTitleByProjectPath();
        workViewApi.loadTitleTree(rootTitle);
    }

    /**
     * 显示窗口
     */
    public void showFrame() {
        workViewApi.showFrame();
    }

    @Override
    public void openTextPaneByTitleNode(ITitleLineVO vo) {
        if (!(vo instanceof TitleLine)) {
            return;
        }
        TitleLine titleLine = ((TitleLine) vo);

        // 检查文件
        AdocFileBeanPath beanPath = fileServiceApi.getFileByProPath(titleLine.getFilePath());
        if (beanPath == null) {
            return;
        }
        // 打开或创建编辑区
        TextPaneView textPaneOfExist = workViewApi.safeCreateAndGetTextPane(beanPath);
        if (textPaneOfExist == null) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_CREATE_AND_OPEN_EDITING_AREA);
        }
        // 如果为空, 加载文件内容
        if (workViewApi.isBlankTextPane(titleLine.getFilePath())) {
            workViewApi.writeTextPaneByFile(beanPath);
        }
        // 添加, 或显示编辑区
        workViewApi.addAndShowTextPane(titleLine.getFilePath());
    }

    @Override
    public void loadTitleNavigate(ITitleLineVO vo) {
        if (!(vo instanceof TitleLine)) {
            return;
        }
        workViewApi.loadTitleNavigate(((TitleLine) vo));
    }

    @Override
    public void positioningLineByTitle(ITitleLineVO vo) {
        if (!(vo instanceof TitleLine)) {
            return;
        }
        workViewApi.positioningLineByTitle(((TitleLine) vo));
    }

    @Override
    public void saveAllEdited() {
        Function<String, WriterVO> getFileWriterFunction = filePath -> fileServiceApi.openFileOutputStream(filePath);
        workViewApi.writeAllEdited(getFileWriterFunction);
    }

    @Override
    public void defaultEnterOperation(@NonNull ActionEvent event) {
        new DefaultEditorKit.InsertBreakAction().actionPerformed(event);
    }

    @Override
    public void generateIncludeSyntaxBySelectedText(GenerateIncludeSyntaxAO ao) {
        if (ao == null) {
            return;
        }
        MatchIncludeInfoBySelectedTextAO includeInfoAO = workViewApi.getIncludeInfoBySelectedText(ao.getFilePath());
        if (includeInfoAO == null) {
            return;
        }
        fileServiceApi.createFileAndInitContent(includeInfoAO);
        workViewApi.replaceSelectedText(ao.getFilePath(), includeInfoAO.getIncludeText());
    }

    @Override
    public void generateTitleSyntaxBySelectedText(GenerateTitleSyntaxAO ao) {
        if (ao == null) {
            return;
        }
        MatchTitleInfoBySelectedTextAO titleInfoAO = workViewApi.getTitleInfoBySelectedText(ao.getFilePath());
        if (titleInfoAO == null) {
            return;
        }
        workViewApi.replaceSelectedText(ao.getFilePath(), titleInfoAO.getTitleText());
    }

    /**
     * todo
     */
    @Override
    public void deleteIncludeOnCaretLine(IncludeFilePathInfoAO ao) {
        workViewApi.replaceSelectedText(ao.getFilePath(), ao.getBlankText());
        fileServiceApi.deleteFileByFilePath(ao.getTargetFilePath());
        workViewApi.closeTextPaneByFilePath(ao.getTargetFilePath());
    }

    @Override
    public void renameInclude(RenameIncludeAO ao) {
        ao.checkRequiredItems();
        // 修改include指向文件的根标题
        AdocFileBeanPath beanPath = fileServiceApi.getFileByProPath(ao.getTargetFilePath());
        TextPaneView textPane = workViewApi.safeCreateAndGetTextPane(beanPath);
        if (textPane == null) {
            throw new ApplicationException(ErrorCodeEnum.INCLUDE_TARGET_TO_THE_FILE_CANNOT_BE_OPENED);
        }
        workViewApi.replaceSelectedText(ao.getTargetFilePath(), ao.getNewName());
        // 修改include块中的文件名
        workViewApi.replaceSelectedText(ao.getFilePath(), ao.getNewName());
        // 修改文件名前, 先保存一次编辑区内容
        saveAllEdited();
        // 修改include指向文件的文件名
        fileServiceApi.renameFile(ao.getTargetFilePath(), ao.getNewName());
        // 关闭旧的指向文件
        workViewApi.closeTextPaneByFilePath(ao.getTargetFilePath());
    }

    @Override
    public void inlineTitle(InlineTitleAO ao) {
        if (ao == null) {
            return;
        }
        ao.checkRequiredItems();
        // include指向文件内容中, include语句相对路径修改
//        String replaceContent = workViewApi.updateRelativePathOfIncludeSyntax(ao);
//        if (StringUtils.isBlank(replaceContent)) {
//            return;
//        }
        // 替换被选择的include行 为include指向文件内容
        workViewApi.replaceSelectedText(ao.getFilePath(), ao.getTargetFileContent());
        // 关闭include指向文件, 从选项卡中
        workViewApi.closeTextPaneByFilePath(ao.getTargetFilePath());
        // 删除include指向文件
        fileServiceApi.deleteFileByFilePath(ao.getTargetFilePath());
    }

    @Override
    public void openInputPrompt(IncludePromptAO ao) {
        workViewApi.openInputPrompt(ao);
        workViewApi.textPaneRequestFocus(ao.getTextPane());
    }

    @Override
    public void closeInputPrompt() {
        workViewApi.closeInputPrompt();
    }

    @Override
    public void selectPromptItem(SelectPromptItemAO ao) {
        workViewApi.selectPromptItem(ao);
    }

    @Override
    public void defaultDownAction(@NonNull ActionEvent event) {
        DefaultEditorKitUtil.downAction(event);
    }

    @Override
    public void defaultUpAction(@NonNull ActionEvent event) {
        DefaultEditorKitUtil.upAction(event);
    }

    @Override
    public void replacePromptItem(@NonNull MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();
        if (!(source instanceof InputPromptMenuItem)) {
            return;
        }
        workViewApi.replacePromptItem(((InputPromptMenuItem) source));
        workViewApi.replacePromptItemForPackage(((InputPromptMenuItem) source));
    }

    /**
     * todo
     */
    @Override
    public void generateJavaIncludeSyntaxBySelectedText(GenerateJavaIncludeSyntaxAO ao) {
        if (ao == null) {
            return;
        }
        workViewApi.generateJavaIncludeSyntaxBySelectedText(ao);
    }

    /**
     * todo
     */
    @Override
    public void openJavaFile(TargetFilePathByIncludeJavaLineAO ao) {
        if (ao == null || StringUtils.isBlank(ao.getAbsolutePath())) { // todo 参数校验待调整
            return;
        }
        boolean isExist = workViewApi.isExistJavaTextPane(ao);
        // 在卡片面板中显示
        if (isExist) {
            workViewApi.showJavaTextPane(ao);
            return;
        }
        //// 打开一个新文件
        // 读取文件内容
        File file = new File(ao.getAbsolutePath());
        if (!file.exists() || !file.isFile()) {
            return;
        }
        // 生成编辑控件
        workViewApi.addJavaTextPane(ao);
    }

    /**
     * todo
     */
    @Override
    public void markLine(MarkLineAO ao) {
        if (ao == null || ao.checkRequiredItems()) {
            return;
        }
        workViewApi.markLine(ao);
    }

    /**
     * todo
     */
    @Override
    public void promptToSaveSuccess() {
        ShowMessageDialogView messageDialog = new ShowMessageDialogView().init(PromptMessageEnum.AUTO_SAVE_SUCCESSFULLY);
        messageDialog.showAndClose();
    }

    /**
     * todo
     */
    @Override
    public void closeFrame() {
        workViewApi.closeFrame();
    }

    @Override
    public void sinkTitle(ITitleContentAO iao) {
        if (!(iao instanceof TitleContentAO)) {
            return;
        }
        TitleContentAO ao = ((TitleContentAO) iao);
        // 获取选择内容
        String selectedContent = workViewApi.getSelectContentByFilePath(ao.getFilePath());

        List<String> deleteFilePaths = new ArrayList<>();
        String newFileText = workViewApi.batchHandleFilePathInIncludeSyntax(selectedContent, ao.getFilePath(), deleteFilePaths);
        if (StringUtils.isBlank(newFileText)) {
            return;
        }
        // 创建include指向文件
        // 向include指向文件中写入, 两个空白行, 选择内容
        ICreateFileAndInitContentAO createAO = ao.getCreateFileAO()
                .addTwoBlankLine()
                .addText(newFileText);
        fileServiceApi.createFileAndInitContent(createAO);
        // 生成include语句内容
        String includeLineText = ao.getIncludeLineText();
            // 替换到选中区域
        workViewApi.replaceSelectedText(ao.getFilePath(), includeLineText);

        this.saveAllEdited();
        // 删除旧的指向的文件
        if (CollectionUtils.isNotEmpty(deleteFilePaths)) {
            deleteFilePaths.forEach(filePath -> {
                try {
                    workViewApi.closeTextPaneByFilePath(filePath);
                    fileServiceApi.deleteFileByFilePath(filePath);
                } catch (Exception e) {
                    log.error("标题下沉-删除include指向文件  失败, filePath={}", filePath, e);
                }
            });
        }
    }
}
