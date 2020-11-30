package com.rh.note.util;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;

/**
 * 树节点图标工具
 */
public class TreeNodeIconUtil {

    public static final Icon readmeRoot = setIconSize(new ImageIcon("icon/file/readme_root.png"));
    public static final Icon readmeChildren = setIconSize(new ImageIcon("icon/file/readme_children.png"));
//    public static final Icon readmeChildren = setIconSize(new ImageIcon("icon/file/twoLevel_children.png"));
    public static final Icon twoLevelRoot = setIconSize(new ImageIcon("icon/file/twoLevel_root.png"));
    public static final Icon twoLevelChildren = setIconSize(new ImageIcon("icon/file/twoLevel_children.png"));
//    public static final Icon twoLevelChildren = setIconSize(new ImageIcon("icon/file/readme_children.png"));
    public static final Icon content = setIconSize(new ImageIcon("icon/file/content_dark.png"));
    public static final Icon unknown = twoLevelChildren;

    /**
     * 设置图片大小
     */
    private static Icon setIconSize(ImageIcon icon) {
        if (icon == null) {
            return null;
        }

        Image image = icon.getImage().getScaledInstance(16, 18, Image.SCALE_DEFAULT);
        icon.setImage(image);
        return icon;
    }

}
