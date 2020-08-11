package com.rh.note.view

import com.rh.note.model.file.Title
import com.rh.note.util.SwingComponent
import org.apache.commons.collections4.CollectionUtils

/**
 * 标题列表根节点
 */
class RootNode implements SwingComponent {

    private Title title

    RootNode(Title title) {
        this.title = title
    }

    void init() {
        init{}
    }

    @Override
    void init(Closure children) {
        swingBuilder.node(id: id(),
                userObject: title,
        ) {
            this.setChildrenNode(title)
        }
    }

    /**
     * 递归组装子节点
     */
    private void setChildrenNode(Title title) {
        if (title == null || CollectionUtils.isEmpty(title.getChildrenTitle())) {
            return
        }

        title.getChildrenTitle().each {
            swingBuilder.node(userObject: it) {
                this.setChildrenNode(it)
            }
        }
    }

    static String id() {
        'tree_node'
    }
}
