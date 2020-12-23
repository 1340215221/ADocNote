package com.rh.note.view;

import com.rh.note.base.BeanPath;
import com.rh.note.builder.TextPaneBuilder;
import com.rh.note.common.IPrototypeView;
import com.rh.note.component.AdocScrollPane;
import com.rh.note.path.AdocFileBeanPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Component;

/**
 * 编辑区滚动面板视图
 */
public class TextScrollPaneView extends IPrototypeView<TextPaneBuilder, AdocScrollPane> {

    @Override
    public @Nullable TextScrollPaneView init(String filePath) {
        return super.init(filePath);
    }

    /**
     * todo private
     */
    public @NotNull AdocScrollPane scrollPane() {
        return super.getComponent(TextPaneBuilder::getScrollPane);
    }

    /**
     * 控件转view
     */
    public static @Nullable TextScrollPaneView cast(Component component) {
        if (!(component instanceof AdocScrollPane)) {
            return null;
        }
        BeanPath beanPath = ((AdocScrollPane) component).getBeanPath();
        if (!(beanPath instanceof AdocFileBeanPath)) {
            return null;
        }
        return new TextScrollPaneView().init(((AdocFileBeanPath) beanPath).getFilePath());
    }

    public @NotNull String getFilePath() {
        AdocFileBeanPath beanPath = (AdocFileBeanPath) scrollPane().getBeanPath();
        return beanPath.getFilePath();
    }
}
