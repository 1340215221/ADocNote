package com.rh.note.view;

import com.rh.note.bean.ITitleLine;
import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import com.rh.note.util.Init;

import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 * 标签树视图
 */
public class TitleTreeView extends Init<JTree> {

    public TitleTreeView init() {
        return super.init(TitleTreeBuilder.treeId());
    }

    /**
     * 展开所有节点
     */
    public void expandAllRow() {
        for (int i = 0; i < tree().getRowCount(); i++) {
            tree().expandRow(i);
        }
    }

    private JTree tree() {
        return getBean();
    }

    /**
     * 获得被选择节点指向的文件
     */
    public AdocFile getAdocFileOfSelectedNode() {
        TreePath path = tree().getSelectionPath();
        if (path == null) {
            return null;
        }
        Object node = path.getLastPathComponent();
        if (!(node instanceof TitleTreeNode)) {
            return null;
        }
        ITitleLine titleLine = ((TitleTreeNode) node).getTitleLine();
        if (!(titleLine instanceof TitleLine)) {
            return null;
        }
        return ((TitleLine) titleLine).getAdocFile();
    }
}
