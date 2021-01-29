package com.rh.note.view;

import cn.hutool.core.io.IoUtil;
import com.rh.note.builder.AdocTextPaneBuilder;
import com.rh.note.common.BaseView;
import com.rh.note.component.AdocTextPane;
import com.rh.note.exception.AdocTextPaneInitContentException;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.TextPaneWriterToFileException;
import com.rh.note.path.FileBeanPath;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.io.Writer;

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

    /**
     * 写入文件
     */
    public void writerToFile(Writer writer) {
        if (writer == null) {
            return;
        }
        try {
            textPane().write(writer);
        } catch (Exception e) {
            throw new TextPaneWriterToFileException(e);
        } finally {
            IoUtil.close(writer);
        }
    }

    /**
     * 初始化编辑区内容
     */
    public void initContent(Reader reader) {
        if (reader == null) {
            return;
        }
        try {
            textPane().read(reader, null);
        } catch (Exception e) {
            throw new AdocTextPaneInitContentException(e);
        } finally {
            IoUtil.close(reader);
        }
    }

    /**
     * 关闭编辑区
     */
    public void close() {
        super.destroy();
    }
}
