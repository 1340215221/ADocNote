package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.RootTitleNodeBuilder;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.vo.TitleLineVO;
import org.jetbrains.annotations.NotNull;

/**
 * 根节点视图
 */
public class RootTitleNodeView extends Init<TitleTreeNode> {

    public static void create(TitleLineVO vo) {
        new RootTitleNodeBuilder(vo).init();
    }

    public @NotNull RootTitleNodeView init() {
        return super.init(RootTitleNodeBuilder.id());
    }

}
