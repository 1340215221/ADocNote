package com.rh.note.event;

import com.rh.note.action.WorkAction;
import com.rh.note.annotation.ComponentBean;
import com.rh.note.constants.FrameCategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;

@ComponentBean(FrameCategoryEnum.WORK)
public class TabbedPaneEvent {

    @Autowired
    private WorkAction workAction;

    /**
     * 加载标题导航, 通过被选择的标签
     */
    public void load_title_navigate_on_selected_tab() {
        // todo
    }

    /**
     * 关闭其他编辑区
     */
    public void closeOtherTextPane() {
        workAction.closeTextPaneNotSelected();
    }

    /**
     * 保存所有编辑区内容
     */
    public void save_all_edit_text() {
        // 保存编辑区所有文件
        workAction.saveAllTextPaneFile();
        // 更新标题树
        workAction.loadRootNode();
    }

    /**
     * 关闭当前编辑区
     */
    public void closeCurrentTextPane() {
        workAction.closeCurrentTextPane();
    }
}
