package com.rh.note.view;

import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.common.BaseView;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultTreeModel;

/**
 * 标题树树模板
 */
public class TreeModelView extends BaseView<TitleTreeBuilder, DefaultTreeModel> {

    public @NotNull TreeModelView init() {
        return super.init();
    }

    protected @NotNull DefaultTreeModel model() {
        return super.getComponent(TitleTreeBuilder::getModel);
    }

    public void setRootNode(RootTitleNodeView nodeView) {
        if (nodeView == null) {
            return;
        }
        model().setRoot(nodeView.node());
    }
}
