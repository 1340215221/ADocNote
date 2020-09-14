package com.rh.note.view;

import com.rh.note.builder.RootTitleNodeBuilder;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.line.TitleLine;
import com.rh.note.util.Init;

/**
 * 根节点视图
 */
public class RootTitleNodeView extends Init<TitleTreeNode> {

    public static void create(TitleLine rootTitle) {
        new RootTitleNodeBuilder(rootTitle).init();
    }

    public RootTitleNodeView init() {
        return super.init(RootTitleNodeBuilder.id());
    }

}
