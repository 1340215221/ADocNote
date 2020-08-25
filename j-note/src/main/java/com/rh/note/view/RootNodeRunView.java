package com.rh.note.view;

import com.rh.note.grammar.TitleGrammar;
import com.rh.note.builder.RootNodeBuilder;
import com.rh.note.view.Init;

import javax.swing.tree.DefaultMutableTreeNode;

public class RootNodeRunView extends Init<DefaultMutableTreeNode> {

    public static void create(TitleGrammar title) {
        if (title == null) {
            return;
        }
        new RootNodeBuilder(title).init();
    }

    public RootNodeRunView init() {
        return init(RootNodeBuilder.id());
    }

}
