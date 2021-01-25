package com.rh.note.view;

import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.common.BaseView;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

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
}
