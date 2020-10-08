package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TitleTreeBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.JTree;

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
}
