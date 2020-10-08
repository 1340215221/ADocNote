package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.component.AdocScrollPane;
import org.jetbrains.annotations.Nullable;

import javax.swing.JScrollPane;

/**
 * 编辑区滚动面板视图
 */
public class TextScrollPaneView extends Init<AdocScrollPane> {

    public @Nullable TextScrollPaneView initByFilePath(String filePath) {
        return super.init(TextPaneBuilder.scrollId(filePath));
    }

}
