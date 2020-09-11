package com.rh.note.builder

import com.rh.note.bean.ITitleLine
import com.rh.note.util.ISwingBuilder
import org.apache.commons.collections4.CollectionUtils

/**
 * 工作窗口-标题列表根节点
 */
class RootTitleNodeBuilder implements ISwingBuilder {

    private ITitleLine titleLine

    RootTitleNodeBuilder(ITitleLine titleLine) {
        this.titleLine = titleLine
    }

    void init() {
        swingBuilder.node(id: id(),
                userObject: titleLine.getTitleName(),
                titleLine: titleLine,
        ) {
            this.setChildrenNode(titleLine)
        }
    }

    /**
     * 递归组装子节点
     */
    private void setChildrenNode(ITitleLine title) {
        if (title == null || CollectionUtils.isEmpty(title.getChildrenTitles())) {
            return
        }

        title.getChildrenTitles().each { children ->
            swingBuilder.node(userObject: children.getTitleName(),
                    titleLine: children,
            ) {
                this.setChildrenNode(children)
            }
        }
    }

    static String id() {
        'tree_node'
    }
}
