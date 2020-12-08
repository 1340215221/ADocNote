package com.rh.note.event;


import com.rh.note.action.IOperationAction;
import com.rh.note.action.IWorkAction;
import com.rh.note.annotation.WorkSingleton;
import com.rh.note.vo.ITitleLineVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 标题树 事件
 */
@WorkSingleton
public class TitleTreeEvent {

    @Autowired
    private IOperationAction operationAction;
    @Autowired
    private IWorkAction workAction;

    /**
     * 点击标题树节点
     */
    public void clicked_title_node() {
        ITitleLineVO vo = operationAction.getSelectedTitleNode();
        if (vo == null) {
            return;
        }
        // 打开标题节点对应的文件
        workAction.openTextPaneByTitleNode(vo);
        // 光标定位到标题所在行, 文件根节点情况除外
        workAction.positioningLineByTitle(vo);
        // 加载标题导航
        ITitleLineVO vo2 = operationAction.getTitleByCaretLineContent();
        if (vo2 != null) {
            workAction.loadTitleNavigate(vo2);
        }
    }

}
