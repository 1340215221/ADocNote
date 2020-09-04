package com.rh.note.component;

import com.rh.note.grammar.ITitleGrammar;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * JTree显示图标
 */
public class NoteTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (!(value instanceof DefaultMutableTreeNode)) {
            return this;
        }
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
        if (!(userObject instanceof ITitleGrammar)) {
            return this;
        }
        ITitleGrammar titleGrammar = (ITitleGrammar) userObject;
        setIcon(titleGrammar.getIcon());
        return this;
    }
}
