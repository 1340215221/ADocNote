package com.rh.note.component;

import com.rh.note.bean.ITitleLine;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * 标题数节点
 */
public class TitleTreeNode extends DefaultMutableTreeNode {

    /**
     * 标题
     */
    private ITitleLine titleLine;

    public ITitleLine getTitleLine() {
        return titleLine;
    }

    public void setTitleLine(ITitleLine titleLine) {
        this.titleLine = titleLine;
    }
}
