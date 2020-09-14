package com.rh.note.view;

import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.util.Init;

import javax.swing.JScrollPane;

/**
 * 编辑区滚动面板视图
 * {@link com.rh.note.builder.TextPaneBuilder#scrollId}
 */
public class TextScrollPaneView extends Init<JScrollPane> {

    public TextScrollPaneView initByFilePath(String filePath) {
        return super.init(TextPaneBuilder.scrollId(filePath));
    }

}
