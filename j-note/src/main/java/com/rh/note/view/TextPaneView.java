package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.component.AdocTextPane;
import com.rh.note.path.AdocFileBeanPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 编辑区视图
 */
public class TextPaneView extends Init<AdocTextPane> {

    public static void create(AdocFileBeanPath beanPath) {
        if (beanPath == null) {
            return;
        }
        new TextPaneBuilder(beanPath).init();
    }

    public @Nullable TextPaneView initByFilePath(String filePath) {
        return super.init(TextPaneBuilder.id(filePath));
    }

    private @NotNull AdocTextPane textPane() {
        return getBean();
    }

    /**
     * 编辑区内容是否为空
     */
    public boolean isBlank() {
        return textPane().getDocument().getLength() < 1;
    }
}
