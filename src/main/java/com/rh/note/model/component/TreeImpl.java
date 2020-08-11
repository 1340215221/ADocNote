package com.rh.note.model.component;

import com.rh.note.model.file.Title;
import com.rh.note.view.TitleList;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 文件树
 */
public class TreeImpl extends Init<JTree> {

    public TreeImpl init() {
        return init(TitleList.getTreeId());
    }

    /**
     * 获得选择节点内容
     * @return
     */
    public Title getSelectionUserObject() {
        Object pathComponent = tree().getSelectionPath().getLastPathComponent();
        if (!(pathComponent instanceof DefaultMutableTreeNode)) {
            return null;
        }

        Object userObject = ((DefaultMutableTreeNode) pathComponent).getUserObject();
        return userObject instanceof Title ? (Title) userObject : null;
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
