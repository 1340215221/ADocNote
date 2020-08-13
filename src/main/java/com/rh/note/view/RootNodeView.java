package com.rh.note.view;

import com.rh.note.grammar.TitleGrammar;
import com.rh.note.builder.RootNodeBuilder;
import com.rh.note.model.component.Init;

import javax.swing.tree.DefaultMutableTreeNode;

public class RootNodeView extends Init<DefaultMutableTreeNode> {

    public static void create(TitleGrammar title) {
        if (title == null) {
            return;
        }
        new RootNodeBuilder(title).init();
    }

    public RootNodeView init() {
        return init(RootNodeBuilder.id());
    }

}
