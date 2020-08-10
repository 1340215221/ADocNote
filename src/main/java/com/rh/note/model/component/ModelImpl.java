package com.rh.note.model.component;

import com.rh.note.view.TitleList;

import javax.swing.tree.DefaultTreeModel;

/**
 * 标题树模板
 */
public class ModelImpl extends Init<DefaultTreeModel> {

    public ModelImpl init() {
        return init(TitleList.getModelId());
    }

    /**
     * 设置根节点
     */
    public void setRoot(RootNodeImpl rootNode) {
        model().setRoot(rootNode.getBean());
    }

    private DefaultTreeModel model() {
        return getBean();
    }
}
