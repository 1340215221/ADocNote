package com.rh.note.view;

import com.rh.note.builder.TextAreaBuilder;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.io.File;

/**
 * 编辑区滚动面板
 */
public class TextAreaScrollView extends Init<JScrollPane> {

    /**
     * 判断该文件是否已经打开
     */
    @Override
    public TextAreaScrollView init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        return super.init(TextAreaBuilder.scrollId(filePath));
    }

    private JScrollPane scrollPane() {
        return getBean();
    }

    /**
     * 将当前控件添加到编辑面板
     * 展示该控件
     */
    public void addTo(EditAreaView editArea, File file) {
        if (editArea == null || file == null) {
            return;
        }

        editArea.getBean().addTab(file.getName(), scrollPane());
    }
}
