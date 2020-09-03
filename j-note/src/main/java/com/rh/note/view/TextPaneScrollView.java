package com.rh.note.view;

import com.rh.note.builder.TextPaneBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;

/**
 * 编辑区滚动面板
 */
public class TextPaneScrollView extends Init<JScrollPane> {

    /**
     * 判断该文件是否已经打开
     */
    @Override
    public TextPaneScrollView init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        return super.init(TextPaneBuilder.scrollId(filePath));
    }

    private JScrollPane scrollPane() {
        return getBean();
    }

    /**
     * 获得id
     */
    public String getId() {
        return scrollPane().getName();
    }

    /**
     * 将当前控件添加到编辑面板
     * 展示该控件
     */
    public void addTo(EditAreaView editArea) {
        if (editArea == null) {
            return;
        }

        String filePath = scrollPane().getName();
        String showName = filePath.substring(filePath.lastIndexOf("/") + 1);
        showName = showName.replace("text_pane_scroll_", "");
        editArea.getBean().add(scrollPane(), showName);
    }

    /**
     * 关闭当前编辑区
     */
    public void closeFrom(EditAreaView editArea) {
        if (editArea == null) {
            return;
        }
        editArea.getBean().remove(scrollPane());
    }
}
