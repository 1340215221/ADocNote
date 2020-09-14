package com.rh.note.view;

import com.rh.note.builder.TitleTreeBuilder;
import com.rh.note.util.Init;

import javax.swing.tree.DefaultTreeModel;

/**
 * 根节点视图
 */
public class TitleTreeModelView extends Init<DefaultTreeModel> {

    public TitleTreeModelView init() {
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

    private DefaultTreeModel model() {
        return getBean();
    }
}
