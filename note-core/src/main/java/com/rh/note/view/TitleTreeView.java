package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.vo.ITitleLineVO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * 标签树视图
 */
public class TitleTreeView extends Init<JTree> {

    public @NotNull TitleTreeView init() {
        return super.init(TitleTreeBuilder.treeId());
    }

    private @NotNull JTree tree() {
        return getBean();
    }

    /**
     * 展开所有节点
     */
    public void expandRowAllNode() {
        for (int i = 0; i < tree().getRowCount(); i++) {
            tree().expandRow(i);
        }
    }

    /**
     * 获得选择的标题节点
     */
    public @Nullable ITitleLineVO getSelectedTitleNode() {
        TreePath selectionPath = tree().getSelectionPath();
        if (selectionPath == null) {
            return null;
        }
        Object titleTreeNode = selectionPath.getLastPathComponent();
        if (!(titleTreeNode instanceof TitleTreeNode)) {
            return null;
        }
        return ((TitleTreeNode) titleTreeNode).getVo();
    }
}
