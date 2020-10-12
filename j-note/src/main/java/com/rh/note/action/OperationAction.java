package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.ao.GenerateIncludeSyntaxAO;
import com.rh.note.ao.GenerateTitleSyntaxAO;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.component.AdocTextPane;
import com.rh.note.component.TitleButton;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.vo.ITitleLineVO;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.NonNull;
import lombok.Setter;
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
        if (!(event instanceof KeyEvent) || event.getID() != KeyEvent.KEY_PRESSED) {
            return false;
        }
        KeyEvent keyEvent = (KeyEvent) event;
        return keyEvent.getKeyCode() == 83 && keyEvent.getModifiers() == 2;
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
        Object source = event.getSource();
        if (!(source instanceof AdocTextPane)) {
            return null;
        }
        return workViewApi.getRootTitleOfCaretLineIncludeTargetFile(((AdocTextPane) source));
    }

    @Override
    public ClickedHistoryProjectListAO clickedHistoryProjectList(@NotNull MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() < 2) {
            return null;
        }
        Object source = mouseEvent.getSource();
        if (!(source instanceof JList)) {
            return null;
        }
        return proManageViewApi.getSelectedHistoryProjectAO(((JList<RecentlyOpenedRecordVO>) source));
    }
}
