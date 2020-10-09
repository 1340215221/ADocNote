package com.rh.note.action;

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
        // 显示已打开的编辑区
        TextPaneView textPaneOfExist = workViewApi.showExistTextPane(titleLine.getFilePath());
        if (textPaneOfExist != null) {
            return;
        }
        // 打开并显示编辑区
        AdocFileBeanPath beanPath = fileServiceApi.getFileByProPath(titleLine.getFilePath());
        workViewApi.createTextPaneByFile(beanPath);
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
    public void generateIncludeSyntax() {
    }
}
