package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TextPaneBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.JScrollPane;

/**
 * 编辑区滚动面板视图
 * {@link TextPaneBuilder#scrollId}
 */
public class TextScrollPaneView extends Init<JScrollPane> {

    public @Nullable TextScrollPaneView initByFilePath(String filePath) {
        return super.init(TextPaneBuilder.scrollId(filePath));
    }

}
