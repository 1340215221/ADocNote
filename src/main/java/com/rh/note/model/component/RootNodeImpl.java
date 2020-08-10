package com.rh.note.model.component;

import com.rh.note.view.RootNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class RootNodeImpl extends Init<DefaultMutableTreeNode> {

    public RootNodeImpl init() {
        return init(RootNode.id());
    }

}
