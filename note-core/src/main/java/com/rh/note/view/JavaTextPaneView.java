package com.rh.note.view;

import cn.hutool.core.io.IoUtil;
import com.rh.note.builder.JavaTextPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.JavaTextPane;
import com.rh.note.exception.TextPaneInitContentException;
import com.rh.note.path.FileBeanPath;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;

/**
 * java编辑区
 */
public class JavaTextPaneView extends BaseView<JavaTextPaneBuilder, JavaTextPane> {

    public @NotNull JavaTextPaneView create(@NonNull FileBeanPath beanPath) {
        return super.create(beanPath);
    }

    public @Nullable JavaTextPaneView init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return super.init(filePath);
    }

    /**
     * 初始化内容
     */
    public void initContent(Reader reader) {
        if (reader == null) {
            return;
        }
        try {
            textPane().read(reader, null);
        } catch (Exception e) {
            throw new TextPaneInitContentException(e);
        } finally {
            IoUtil.close(reader);
        }
    }

    protected @NotNull JavaTextPane textPane() {
        return super.getComponent(JavaTextPaneBuilder::getTextPane);
    }

    /**
     * 关闭编辑区
     */
    public void close() {
        super.destroy();
    }
}
