package com.rh.note.model.component;

import com.rh.note.view.TextArea;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;

/**
 * 编辑区滚动面板
 */
public class TextAreaScrollImpl extends Init<JScrollPane> {

    /**
     * 判断该文件是否已经打开
     */
    @Override
    public TextAreaScrollImpl init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        return super.init(TextArea.scrollId(filePath));
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
    public void addTo(EditAreaImpl editArea) {
        if (editArea == null) {
            return;
        }

        editArea.getBean().add(scrollPane(), scrollPane().getName());
    }
}
