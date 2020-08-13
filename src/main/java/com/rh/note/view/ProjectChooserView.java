package com.rh.note.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * 选择项目文件夹
 */
public class ProjectChooserView {

    public static String showOpenDialog(Component component) {
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

    public String showOpenDialog() {
        ProjectListView projectList = new ProjectListView().init();
        return ProjectChooserView.showOpenDialog(projectList.getBean());
    }

}
