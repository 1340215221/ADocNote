package com.rh.note.view;

import com.rh.note.base.Init;
import com.rh.note.builder.JavaTextPaneBuilder;
import com.rh.note.component.JavaScrollPane;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * java文件编辑区滚动
 */
public class JavaScrollPaneView extends Init<JavaScrollPane> {

    @Override
    public @Nullable JavaScrollPaneView init(String absolutePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return null;
        }
        return super.init(JavaTextPaneBuilder.scrollId(absolutePath));
    }

    private @NotNull JavaScrollPane scrollPane() {
        return getBean();
    }
}
