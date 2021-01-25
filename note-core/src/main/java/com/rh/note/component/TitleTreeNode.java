package com.rh.note.component;

import com.rh.note.line.TitleLine;
import lombok.Data;

import javax.swing.tree.DefaultMutableTreeNode;

@Data
public class TitleTreeNode extends DefaultMutableTreeNode {
    /**
     * 标题属性
     */
    private TitleLine titleLine;
}
