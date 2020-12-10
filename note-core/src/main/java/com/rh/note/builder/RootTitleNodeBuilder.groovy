package com.rh.note.builder

import com.rh.note.annotation.WorkPrototype
import com.rh.note.common.IPrototypeBuilder
import com.rh.note.vo.ITitleLineVO
import groovy.swing.SwingBuilder
import org.apache.commons.collections4.CollectionUtils
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

/**
 * 工作窗口-标题列表根节点
 */
@WorkPrototype(builder_name)
class RootTitleNodeBuilder implements IPrototypeBuilder {

    static final String builder_name = "tree_node"
    @Autowired
    private SwingBuilder swingBuilder
    private ITitleLineVO vo

    RootTitleNodeBuilder(ITitleLineVO vo) {
        this.vo = vo
    }

    @Override
    @PostConstruct
    void init() {
        swingBuilder.ttNode(id: id(),
                userObject: vo.getTitleName(),
                vo: vo,
        ) {
            this.setChildrenNode(vo)
        }
    }

    /**
     * 递归组装子节点
     */
    private void setChildrenNode(ITitleLineVO vo) {
        if (vo == null || CollectionUtils.isEmpty(vo.getChildrenTitles())) {
            return
        }

        vo.getChildrenTitles().each { children ->
            swingBuilder.ttNode(id: nodeId(children.getBeanPathStr()),
                    userObject: children.getTitleName(),
                    vo: children,
            ) {
                this.setChildrenNode(children)
            }
        }
    }

    @Override
    @PreDestroy
    void destroy() {
        swingBuilder.variables.remove(id())
        swingBuilder.variables.removeAll {String key, value ->
            key.startsWith(nodeId(""))
        }
    }

    @Override
    String getInstanceName() {
        return id()
    }

    static String id() {
        'tree_node'
    }

    static String nodeId(String beanPath) {
        "tree_node_${beanPath}"
    }
}
