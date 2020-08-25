package com.rh.note.view;

import com.rh.note.builder.TitleListBuilder;
import com.rh.note.grammar.TitleGrammar;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 文件树
 */
public class TreeView extends Init<JTree> {

    public TreeView init() {
        return init(TitleListBuilder.getTreeId());
    }

    /**
     * 获得选择节点内容
     * @return
     */
    public TitleGrammar getSelectionUserObject() {
        Object pathComponent = tree().getSelectionPath().getLastPathComponent();
        if (!(pathComponent instanceof DefaultMutableTreeNode)) {
            return null;
        }

        Object userObject = ((DefaultMutableTreeNode) pathComponent).getUserObject();
        return userObject instanceof TitleGrammar ? (TitleGrammar) userObject : null;
    }

    private JTree tree() {
        return getBean();
    }

    /**
     * 展开所有节点
     */
    public void expandAllRow() {
        for (int i = 0; i < tree().getRowCount(); i++) {
            tree().expandRow(i);
        }
    }
}
