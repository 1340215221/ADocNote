package com.rh.note.view;

import com.rh.note.builder.TitleListBuilder;
import com.rh.note.view.Init;

import javax.swing.tree.DefaultTreeModel;

/**
 * 标题树模板
 */
public class ModelView extends Init<DefaultTreeModel> {

    public ModelView init() {
        return init(TitleListBuilder.getModelId());
    }

    /**
     * 设置根节点
     */
    public void setRoot(RootNodeRunView rootNode) {
        if (rootNode == null) {
            return;
        }
        model().setRoot(rootNode.getBean());
    }

    private DefaultTreeModel model() {
        return getBean();
    }
}
