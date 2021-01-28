package com.rh.note.view;

import com.rh.note.builder.AdocTextPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.TextScrollPane;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * 编辑区滚动面板 视图
 */
public class TextScrollPaneView extends BaseView<AdocTextPaneBuilder, TextScrollPane> {

    public @Nullable TextScrollPaneView init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return super.init(filePath);
    }

    protected @NotNull TextScrollPane scrollPane() {
        return super.getComponent(AdocTextPaneBuilder::getScrollPane);
    }

}
