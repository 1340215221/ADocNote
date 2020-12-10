package com.rh.note.view;

import com.rh.note.builder.JavaTextPaneBuilder;
import com.rh.note.common.IPrototypeView;
import com.rh.note.component.JavaScrollPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * java文件编辑区滚动
 */
public class JavaScrollPaneView extends IPrototypeView<JavaTextPaneBuilder, JavaScrollPane> {

    @Override
    public @Nullable JavaScrollPaneView init(String absolutePath) {
        return super.init(absolutePath);
    }

    private @NotNull JavaScrollPane scrollPane() {
        return super.getComponent(JavaTextPaneBuilder::getScrollPane);
    }
}
