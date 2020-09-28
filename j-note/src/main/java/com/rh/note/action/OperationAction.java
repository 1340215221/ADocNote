package com.rh.note.action;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.api.ProManageViewApi;
import com.rh.note.api.WorkViewApi;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.JList;
import java.awt.event.MouseEvent;

@Setter
public class OperationAction implements IOperationAction {

    private ProManageViewApi proManageViewApi;
    private WorkViewApi workViewApi;

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
