package com.rh.note.view;

import com.rh.note.model.component.ProjectListImpl;
import com.rh.note.model.component.WorkFrameImpl;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Objects;

/**
 * 项目选择
 */
public class ProjectChooser {

    /**
     * 选择项目文件夹
     */
    public String showOpenDialog(Component component) {
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

}
