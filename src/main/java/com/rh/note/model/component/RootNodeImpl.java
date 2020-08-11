package com.rh.note.model.component;

import com.rh.note.model.file.Title;
import com.rh.note.view.RootNode;

import javax.swing.tree.DefaultMutableTreeNode;

public class RootNodeImpl extends Init<DefaultMutableTreeNode> {

    public static void create(Title title) {
        if (title == null) {
            return;
        }
        new RootNode(title).init();
    }

    public RootNodeImpl init() {
        return init(RootNode.id());
    }

}
