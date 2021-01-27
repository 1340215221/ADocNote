package com.rh.note.builder

import com.rh.note.annotation.ComponentBean
import com.rh.note.common.BaseBuilder
import com.rh.note.component.TitleTreeNode
import com.rh.note.constants.FrameCategoryEnum
import com.rh.note.constants.ScopeEnum
import com.rh.note.factory.TitleTreeNodeFactory
import com.rh.note.line.TitleLine
import groovy.swing.SwingBuilder
import org.apache.commons.collections4.CollectionUtils
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

/**
 * 根标题节点
 */
@ComponentBean(frame = FrameCategoryEnum.WORK, scope = ScopeEnum.PROTOTYPE, name = RootTitleNodeBuilder.id)
class RootTitleNodeBuilder implements BaseBuilder {

    public static final String id = 'root_node_{}'
    @Autowired
    private SwingBuilder swingBuilder
    @Autowired
    private TitleTreeNodeFactory factory
    private TitleLine titleLine

    RootTitleNodeBuilder(TitleLine titleLine) {
        this.titleLine = titleLine
    }

    @PostConstruct
    void init() {
        swingBuilder.ttNode(id: id,
                userObject: this.titleLine.getTitleName(),
                titleLine: this.titleLine,
        ) {
            initChildrenNode(titleLine)
        }
    }

    /**
     * 递归组装子节点
     */
    private void initChildrenNode(TitleLine titleLine) {
        if (titleLine == null || CollectionUtils.isEmpty(titleLine.getChildrenTitle())) {
            return
        }

        titleLine.getChildrenTitle().each { children ->
            swingBuilder.ttNode(id: nodeId(children.getBeanPathStr()),
                    userObject: children.getTitleName(),
                    vo: children,
            ) {
                this.initChildrenNode(children)
            }
        }
    }

    /**
     * 生成节点id
     */
    private String nodeId(String beanPath) {
        return "tree_node_${beanPath}"
    }

    TitleTreeNode getNode() {
        return swingBuilder."${id}"
    }
}
