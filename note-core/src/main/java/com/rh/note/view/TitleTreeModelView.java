package com.rh.note.view;

import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.common.ISingletonView;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultTreeModel;

/**
 * 根节点视图
 */
public class TitleTreeModelView extends ISingletonView<TitleTreeBuilder, DefaultTreeModel> {

    public @NotNull TitleTreeModelView init() {
        return super.init();
    }

    /**
     * 设置根节点
     */
    public void setRoot(RootTitleNodeView rootTitleNode) {
        if (rootTitleNode == null) {
            return;
        }

        model().setRoot(rootTitleNode.treeNode());
    }

    private @NotNull DefaultTreeModel model() {
        return super.getComponent(TitleTreeBuilder::getModel);
    }
}
