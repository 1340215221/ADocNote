package com.rh.note.view;

import com.rh.note.builder.RootTitleNodeBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.line.TitleLine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 根标题节点
 */
public class RootTitleNodeView extends BaseView<RootTitleNodeBuilder, TitleTreeNode> {

    /**
     * beanName参数
     */
    public static final String[] beanArg = new String[]{"install"};

    public @Nullable RootTitleNodeView init() {
        return super.init(beanArg);
    }

    public @NotNull RootTitleNodeView create(@NotNull TitleLine rooTitle) {
        return super.create(rooTitle);
    }

    protected @NotNull TitleTreeNode node() {
        return super.getComponent(RootTitleNodeBuilder::getNode);
    }

    /**
     * 删除所有节点
     */
    public void deleteAllNote() {
        super.destroy();
    }
}
