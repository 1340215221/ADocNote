package com.rh.note.view;

import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.common.ISingletonView;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.vo.ITitleLineVO;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * 标签树视图
 */
public class TitleTreeView extends ISingletonView<TitleTreeBuilder, JTree> {

    public @NotNull TitleTreeView init() {
        return super.init();
    }

    private @NotNull JTree tree() {
        return super.getComponent(TitleTreeBuilder::getTree);
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
