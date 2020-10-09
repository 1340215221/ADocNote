package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.component.TitleButton;
import com.rh.note.line.TitleLine;
import com.rh.note.vo.ITitleLineVO;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JList;
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
    public ITitleLineVO getSelectedTextPane() {
        return workViewApi.getSelectedTextPane();
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
    public boolean checkIsFileRootTitle(ITitleLineVO vo) {
        if (!(vo instanceof TitleLine)) {
            return false;
        }
        return workViewApi.checkIsFileRootTitle(((TitleLine) vo));
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
