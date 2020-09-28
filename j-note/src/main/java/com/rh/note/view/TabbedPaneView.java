package com.rh.note.view;

import com.rh.note.bean.IAdocFile;
import com.rh.note.builder.TabbedPaneBuilder;
import com.rh.note.component.TitleScrollPane;
import com.rh.note.util.Init;

import javax.swing.JTabbedPane;
import java.awt.Component;

/**
 * 编辑区标签面板视图
 * {@link TabbedPaneBuilder#id}
 */
public class TabbedPaneView extends Init<JTabbedPane> {

    public TabbedPaneView init() {
        return super.init(TabbedPaneBuilder.id());
    }

    /**
     * 添加编辑文件
     */
    public void add(TextScrollPaneView textScrollPane, String fileName) {
        if (textScrollPane == null) {
            return;
        }
        tabbedPane().add(textScrollPane.getBean(), fileName);
    }

    private JTabbedPane tabbedPane() {
        return getBean();
    }

    /**
     * 显示指定编辑区
     */
    public void show(TextScrollPaneView textScrollPane) {
        if (textScrollPane == null) {
            return;
        }

        tabbedPane().setSelectedComponent(textScrollPane.getBean());
    }

    /**
     * 获得文件地址
     */
    public String getFilePath() {
        Component component = tabbedPane().getSelectedComponent();
        if (!(component instanceof TitleScrollPane)) {
            return null;
        }
        IAdocFile adocFile = ((TitleScrollPane) component).getAdocFile();
        return adocFile.getFilePath();
    }
}
