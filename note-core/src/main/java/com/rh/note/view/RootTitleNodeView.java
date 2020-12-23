package com.rh.note.view;

import com.rh.note.builder.RootTitleNodeBuilder;
import com.rh.note.common.IPrototypeView;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.line.TitleLine;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.vo.ITitleLineVO;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 根节点视图
 */
public class RootTitleNodeView extends IPrototypeView<RootTitleNodeBuilder, TitleTreeNode> {

    public @NotNull RootTitleNodeView create(ITitleLineVO vo) {
        return super.create(vo);
    }

    public @NotNull RootTitleNodeView init() {
        return super.init(null);
    }

    protected TitleTreeNode treeNode() {
        return super.getComponent(RootTitleNodeBuilder::getTreeNode);
    }

    /**
     * 获得标题, 通过对象路径
     */
    public @Nullable TitleLine getTitleByBeanPath(TitleBeanPath beanPath) {
        if (beanPath == null) {
            return null;
        }
        TitleLine rootTitle = (TitleLine) treeNode().getVo();
        Deque<TitleLine> deque =  new LinkedList<>();
        deque.push(rootTitle);
        while (true) {
            TitleLine titleLine = deque.pop();
            if (titleLine == null) {
                return null;
            }
            if (titleLine.getFilePath().equals(beanPath.getFilePath())
                    && titleLine.getTitleName().equals(beanPath.getTitleName())
            ) {
                return titleLine;
            }
            List<TitleLine> childrenTitles = titleLine.getChildrenTitles();
            if (CollectionUtils.isEmpty(childrenTitles)) {
                continue;
            }
            childrenTitles.forEach(deque::push);
        }
    }

    /**
     * 清理所有节点
     */
    public void destroy() {
        super.destroy();
    }
}
