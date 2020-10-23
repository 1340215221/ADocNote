package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.ao.GenerateIncludeSyntaxAO;
import com.rh.note.ao.GenerateTitleSyntaxAO;
import com.rh.note.ao.ITitleContentAO;
import com.rh.note.ao.IncludeFilePathInfoAO;
import com.rh.note.ao.LineRangeAO;
import com.rh.note.ao.RenameIncludeAO;
import com.rh.note.ao.TitleContentAO;
import com.rh.note.api.FileServiceApi;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.component.AdocTextPane;
import com.rh.note.component.TitleButton;
import com.rh.note.constants.Keymap;
import com.rh.note.constants.PromptMessageEnum;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.UnknownLogicException;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.view.TextPaneView;
import com.rh.note.vo.ITitleLineVO;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JList;
import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

@Setter
public class OperationAction implements IOperationAction {

    private ProManageViewApi proManageViewApi;
    private WorkViewApi workViewApi;
    private FileServiceApi fileServiceApi;

    @Override
    public ITitleLineVO getSelectedTitleNode() {
        return workViewApi.getSelectedTitleNode();
    }

    @Override
    public ITitleLineVO clickedNavigateButton(@NonNull MouseEvent event) {
        Object source = event.getSource();
        if (!(source instanceof TitleButton)) {
            return null;
        }
        return workViewApi.getFileRootTitleByButton(((TitleButton) source));
    }

    @Override
    public ITitleLineVO getTitleByCaretLineContent() {
        TitleBeanPath beanPath = workViewApi.getSimpleTitleByCaretLineContent();
        if (beanPath == null) {
            return null;
        }
        return workViewApi.getTitleByBeanPath(beanPath);
    }

    @Override
    public boolean checkIsSaveHotKey(@NonNull AWTEvent event) {
        return Keymap.isSaveOperation(event);
    }

    @Override
    public GenerateIncludeSyntaxAO selectCaretLineOfIncludeSyntaxSugar(ActionEvent event) {
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane)) {
            return null;
        }
        AdocTextPane textPane = (AdocTextPane) source;
        boolean isInclude = workViewApi.checkIsIncludeSyntaxSugarOnCaretLine(textPane);
        if (!isInclude) {
            return null;
        }
        workViewApi.selectCaretLine(textPane);
        return new GenerateIncludeSyntaxAO().setTextPane(textPane);
    }

    @Override
    public @Nullable GenerateTitleSyntaxAO selectCaretLineOfTitleSyntaxSugar(@NotNull ActionEvent event) {
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane)) {
            return null;
        }
        AdocTextPane textPane = (AdocTextPane) source;
        boolean isTitle = workViewApi.checkIsTitleSyntaxSugarOnCaretLine(textPane);
        if (!isTitle) {
            return null;
        }
        workViewApi.selectCaretLine(textPane);
        return new GenerateTitleSyntaxAO().setTextPane(textPane);
    }

    @Override
    public ITitleLineVO getRootTitleOfCaretLineIncludeTargetFile(@NonNull MouseEvent event) {
        if (!Keymap.isEnterInclude(event)) {
            return null;
        }
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane)) {
            return null;
        }
        return workViewApi.getRootTitleOfCaretLineIncludeTargetFile(((AdocTextPane) source));
    }

    @Override
    public IncludeFilePathInfoAO deleteIncludeOperation(@NonNull KeyEvent event) {
        if (!Keymap.isSafeDelete(event)) {
            return null;
        }
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane)) {
            return null;
        }
        // 请求确认
        if (!workViewApi.requestConfirm(PromptMessageEnum.are_you_sure_you_want_to_delete_safely_include)) {
            return null;
        }
        // 选择include行内容
        IncludeFilePathInfoAO ao = workViewApi.getIncludeFilePathInfoOnCaretLine(((AdocTextPane) source));
        if (ao != null) {
            workViewApi.selectCaretLine(((AdocTextPane) source));
        }
        return ao;
    }

    @Override
    public RenameIncludeAO renameIncludeOperation(@NonNull KeyEvent event) {
        if (!Keymap.isRename(event)) {
            return null;
        }
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane)) {
            return null;
        }
        // 获得旧标题名
        AdocTextPane textPane = (AdocTextPane) event.getSource();
        String oldName = workViewApi.getIncludeFileNameOnCaretLine(textPane);
        if (StringUtils.isBlank(oldName)) {
            return null;
        }
        // 请求新标题名
        RenameIncludeAO ao = workViewApi.requestNewName(textPane, oldName);
        if (ao == null) {
            return null;
        }
        // 选择include行中的文件名
        String targetFilePath = workViewApi.selectedIncludeFileNameOnCaretLine(textPane);
        // 选择include指向文件的根标题
        AdocFileBeanPath targetBeanPath = fileServiceApi.getFileByProPath(targetFilePath);
        TextPaneView targetTextPane = workViewApi.safeCreateAndGetTextPane(targetBeanPath);
        if (targetTextPane == null) {
            throw new ApplicationException(ErrorCodeEnum.INCLUDE_TARGET_TO_THE_FILE_CANNOT_BE_OPENED);
        }
        if (workViewApi.isBlankTextPane(targetFilePath)) {
            workViewApi.writeTextPaneByFile(targetBeanPath);
        }
        workViewApi.selectRootTitleName(targetFilePath);
        return ao;
    }

    @Override
    public ITitleContentAO sinkTitleOperation(@NonNull KeyEvent event) {
        if (!Keymap.isSinkTitle(event)) {
            return null;
        }
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane)) {
            return null;
        }

        TitleContentAO ao = new TitleContentAO();
        // 判断光标所在行是否为标题, 且非根标题
        TitleLine titleLine = workViewApi.getSimpleTitleLineByCaretLine(((AdocTextPane) source));
        if (titleLine == null || ao.isRootTitle(titleLine)) {
            return null;
        }
        ao.copy(titleLine);
        LineRangeAO lrAO = ao.getTitleContentRange(titleLine);
        if (lrAO == null || !lrAO.isReasonable()) {
            throw new UnknownLogicException();
        }
        // 选择标题下所有内容
        workViewApi.selectLineByRange(lrAO);
        return ao;
    }

    @Override
    public ClickedHistoryProjectListAO clickedHistoryProjectList(@NotNull MouseEvent event) {
        if (!Keymap.isDoubleClick(event)) {
            return null;
        }
        Object source = event.getSource();
        if (!(source instanceof JList)) {
            return null;
        }
        return proManageViewApi.getSelectedHistoryProjectAO(((JList<RecentlyOpenedRecordVO>) source));
    }
}
