package com.rh.note.component;

import com.rh.note.line.TitleLine;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

/**
 * 标题树 回调类
 * 不同的节点显示不同的图标
 */
public class TitleTreeNodeIconRenderer extends DefaultTreeCellRenderer {

    // ReadMe文件根标题
    public final Icon readmeRoot = setIconSize(new ImageIcon("icon/file/readme_root.png"));
    // ReadMe文件子标题
    public final Icon readmeChildren = setIconSize(new ImageIcon("icon/file/readme_children.png"));
    // twoLevel文件根标题
    public final Icon twoLevelRoot = setIconSize(new ImageIcon("icon/file/twoLevel_root.png"));
    // twoLevel文件子标题
    public final Icon twoLevelChildren = setIconSize(new ImageIcon("icon/file/twoLevel_children.png"));
    // content文件标题
    public final Icon content = setIconSize(new ImageIcon("icon/file/content_dark.png"));
    // 未知文件标题
    public final Icon unknown = twoLevelChildren;

    /**
     * 获得树节点图标, 通过节点标题所在目录
     */
    private @NotNull Icon getNodeIconByTitleFilePath(TitleLine titleLine) {
        if (titleLine == null) {
            return unknown;
        }
        boolean isRootTitle = titleLine.isRootTitle();
        if (titleLine.declareInReadMe() && isRootTitle) {
            return readmeRoot;
        }
        if (titleLine.declareInReadMe()) {
            return readmeChildren;
        }
        if (titleLine.declareInTwoLevel() && isRootTitle) {
            return twoLevelRoot;
        }
        if (titleLine.declareInTwoLevel()) {
            return twoLevelChildren;
        }
        if (titleLine.declareInContent()) {
            return content;
        }
        return unknown;
    }

    /**
     * 设置标题图片大小
     */
    private @Nullable Icon setIconSize(ImageIcon icon) {
        if (icon == null) {
            return null;
        }
        Image image = icon.getImage().getScaledInstance(16, 18, Image.SCALE_DEFAULT);
        icon.setImage(image);
        return icon;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (!(value instanceof TitleTreeNode)) {
            return this;
        }
        TitleLine titleLine = ((TitleTreeNode) value).getTitleLine();
        setIcon(getNodeIconByTitleFilePath(titleLine));
        return this;
    }
}
