package com.rh.note.event;


import com.rh.note.action.IOperationAction;
import com.rh.note.action.IWorkAction;
import com.rh.note.annotation.WorkSingleton;
import com.rh.note.vo.ITitleLineVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 文件标签栏 事件
 * {@link }
 */
@WorkSingleton
public class TabbedPaneEvent {

    @Autowired
    private IOperationAction operationAction;
    @Autowired
    private IWorkAction workAction;

    /**
     * 加载标题导航, 在选择文件标签时
     */
    public void load_title_navigate_on_selected_tab() {
        ITitleLineVO vo = operationAction.getTitleByCaretLineContent();
        if (vo == null) {
            return;
        }
        workAction.loadTitleNavigate(vo);
    }

}
