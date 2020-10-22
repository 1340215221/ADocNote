package com.rh.note.action;

import com.rh.note.ao.GenerateIncludeSyntaxAO;
import com.rh.note.ao.GenerateTitleSyntaxAO;
import com.rh.note.ao.IncludeFilePathInfoAO;
import com.rh.note.ao.MatchIncludeInfoBySelectedTextAO;
import com.rh.note.ao.MatchTitleInfoBySelectedTextAO;
import com.rh.note.ao.RenameIncludeAO;
import com.rh.note.api.FileServiceApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.view.TextPaneView;
import com.rh.note.vo.ITitleLineVO;
import com.rh.note.vo.WriterVO;
import lombok.NonNull;
import lombok.Setter;

import javax.swing.text.DefaultEditorKit;
import java.awt.event.ActionEvent;
import java.util.function.Function;

/**
 * 工作窗口 入口
 */
@Setter
public class WorkAction implements IWorkAction {

    private WorkViewApi workViewApi;
    private FileServiceApi fileServiceApi;

    /**
     * 启动窗口
     */
    public void initFrame() {
        TitleLine rootTitle = fileServiceApi.readAllTitleByProjectPath();
        if (rootTitle == null) {
            throw new ApplicationException(ErrorCodeEnum.CANNOT_OPEN_A_PROJECT_WITHOUT_A_TITLE);
        }
        workViewApi.initFrame();
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
    }

    /**
     * todo
     */
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
}
