package com.rh.note.view;

import org.jetbrains.annotations.Nullable;

import javax.swing.JFileChooser;
import java.awt.Component;
import java.io.File;

/**
 * 导入项目弹窗视图
 */
public class ImportProjectDialogView {

    private static @Nullable String showOpenDialog(Component component) {
        if (component == null) {
            return null;
        }
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File("~"));
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = jfc.showOpenDialog(component);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            return file.getAbsolutePath();
        }
        return null;
    }

    public static @Nullable String create(ProManageFrameView proManageFrame) {
        return showOpenDialog(proManageFrame.getBean());
    }
}
