package com.rh.note.view;

import com.rh.note.builder.AdocTextPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.AdocTextPane;
import com.rh.note.exception.AdocTextPaneInitContentException;
import com.rh.note.path.FileBeanPath;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;

/**
 * adoc编辑区 视图
 */
public class AdocTextPaneView extends BaseView<AdocTextPaneBuilder, AdocTextPane> {

    public @Nullable AdocTextPaneView init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return super.init(filePath);
    }

    protected AdocTextPane textPane() {
        return super.getComponent(AdocTextPaneBuilder::getTextPane);
    }

    public @NotNull AdocTextPaneView create(@NotNull FileBeanPath beanPath) {
        return super.create(beanPath);
    }

    public void initContent(Reader reader) {
        if (reader == null) {
            return;
        }
        try {
            textPane().read(reader, null);
        } catch (Exception e) {
            throw new AdocTextPaneInitContentException(e);
        }
    }
}
