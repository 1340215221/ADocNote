package com.rh.note.component;

import com.rh.note.vo.ITitleLineVO;

import javax.swing.tree.DefaultMutableTreeNode;

public class TitleTreeNode extends DefaultMutableTreeNode {

    /**
     * 标题属性
     */
    private ITitleLineVO vo;

    public ITitleLineVO getVo() {
        return vo;
    }

    public void setVo(ITitleLineVO vo) {
        this.vo = vo;
    }
}
