package com.rh.note.view;

import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.line.TitleLine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.TreePath;

/**
 * 标题树
 */
public class TitleTreeView extends BaseView<TitleTreeBuilder, JTree> {

    public @NotNull TitleTreeView init() {
        return super.init();
    }

    protected @NotNull JTree tree() {
        return super.getComponent(TitleTreeBuilder::getTree);
    }

    /**
     * 展开所有节点
     */
    public void expandAllNode() {
        for (int i = 0; i <tree().getRowCount(); i++) {
            tree().expandRow(i);
        }
    }

    /**
     * 获得被选择节点的标题对象
     */
    public @Nullable TitleLine getTitleLineBySelectedNode() {
        TreePath treePath = tree().getSelectionPath();
        if (treePath == null) {
            return null;
        }
        Object lastPathComponent = treePath.getLastPathComponent();
        if (!(lastPathComponent instanceof TitleTreeNode)) {
            return null;
        }
        return ((TitleTreeNode) lastPathComponent).getTitleLine();
    }

    public void expandNodeByLevel(Integer level) {
        if (level == null || level < 1) {
            return;
        }
        for (int i = 0; i < tree().getRowCount(); i++) {
            TreePath treePath = tree().getPathForRow(i);
            if (treePath == null) {
                continue;
            }
            Object lastPathComponent = treePath.getLastPathComponent();
            if (!(lastPathComponent instanceof TitleTreeNode)) {
                continue;
            }
            TitleLine titleLine = ((TitleTreeNode) lastPathComponent).getTitleLine();
            if (titleLine == null) {
                continue;
            }
            Integer currentLevel = titleLine.getLevel();
            if (currentLevel == null) {
                continue;
            }
            if (currentLevel <= level) {
                tree().collapsePath(treePath);
                continue;
            }
            tree().expandPath(treePath);
        }
    }
}
