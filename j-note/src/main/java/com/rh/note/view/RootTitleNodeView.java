package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.RootTitleNodeBuilder;
import com.rh.note.component.TitleTreeNode;
import com.rh.note.line.TitleLine;
import com.rh.note.vo.ITitleLineVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * 根节点视图
 */
public class RootTitleNodeView extends Init<TitleTreeNode> {

    public static void create(ITitleLineVO vo) {
        new RootTitleNodeBuilder(vo).init();
    }

    public @NotNull RootTitleNodeView init() {
        return super.init(RootTitleNodeBuilder.id());
    }

    private TitleTreeNode treeNode() {
        return getBean();
    }

    /**
     * 获得文件根节点, 通过文件路径
     */
    public ITitleLineVO getTitleByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
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
            if (titleLine.getFilePath().equals(filePath)) {
                return titleLine;
            }
            List<TitleLine> childrenTitles = titleLine.getChildrenTitles();
            if (CollectionUtils.isEmpty(childrenTitles)) {
                continue;
            }
            childrenTitles.forEach(deque::push);
        }
    }
}
