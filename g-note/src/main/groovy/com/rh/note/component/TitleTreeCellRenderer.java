package com.rh.note.component;

import com.rh.note.bean.ITitleLine;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Component;

/**
 * 标题树 回调类
 * 不同的节点显示不同的图标
 */
public class TitleTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (!(value instanceof TitleTreeNode)) {
            return this;
        }
        ITitleLine titleLine = ((TitleTreeNode) value).getTitleLine();
        setIcon(titleLine.getTreeNodeIcon());
        return this;
    }
}
