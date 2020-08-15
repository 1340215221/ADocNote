package com.rh.note.builder

import com.rh.note.grammar.TitleGrammar
import com.rh.note.util.SwingComponent
import org.apache.commons.collections4.CollectionUtils

/**
 * 标题列表根节点
 */
class RootNodeBuilder implements SwingComponent {

    private TitleGrammar title

    RootNodeBuilder(TitleGrammar title) {
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
    private void setChildrenNode(TitleGrammar title) {
        if (title == null || CollectionUtils.isEmpty(title.getChildrenTitle())) {
            return
        }

        title.getChildrenTitle().each {children ->
            swingBuilder.node(userObject: children) {
                this.setChildrenNode(children)
            }
        }
    }

    static String id() {
        'tree_node'
    }
}
