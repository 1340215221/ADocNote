package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TitleTreeBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultTreeModel;

/**
 * 根节点视图
 */
public class TitleTreeModelView extends Init<DefaultTreeModel> {

    public @NotNull TitleTreeModelView init() {
        return super.init(TitleTreeBuilder.modelId());
    }

    /**
     * 设置根节点
     */
    public void setRoot(RootTitleNodeView rootTitleNode) {
        if (rootTitleNode == null) {
            return;
        }

        model().setRoot(rootTitleNode.getBean());
    }

    private @NotNull DefaultTreeModel model() {
        return getBean();
    }
}
