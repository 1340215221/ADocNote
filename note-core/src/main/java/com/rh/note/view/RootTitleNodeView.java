package com.rh.note.view;

import com.rh.note.builder.RootTitleNodeBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.line.TitleLine;
import org.jetbrains.annotations.NotNull;

/**
 * 根标题节点
 */
public class RootTitleNodeView extends BaseView<RootTitleNodeBuilder, TitleTreeNode> {

    public @NotNull RootTitleNodeView create(@NotNull TitleLine rooTitle) {
        return super.create(rooTitle);
    }

    protected @NotNull TitleTreeNode node() {
        return super.getComponent(RootTitleNodeBuilder::getNode);
    }
}
