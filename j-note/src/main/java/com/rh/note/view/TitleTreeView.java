package com.rh.note.view;

import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.util.Init;

import javax.swing.JTree;

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
}
