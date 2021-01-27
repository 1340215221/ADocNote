package com.rh.note.component;

import com.rh.note.line.TitleLine;
import lombok.Getter;
import lombok.Setter;

import javax.swing.tree.DefaultMutableTreeNode;

@Getter
@Setter
public class TitleTreeNode extends DefaultMutableTreeNode {
    /**
     * 标题属性
     */
    private TitleLine titleLine;
}
