package com.rh.note.ao;

import cn.hutool.core.io.IoUtil;
import com.rh.note.common.BaseAO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 编辑区内容写入 参数
 */
public class TextPaneFileWritersAO implements BaseAO {

    private final List<Item> items = new ArrayList<>();

    @Override
    public boolean checkMissRequiredParams() {
        return CollectionUtils.isEmpty(items);
    }

    /**
     * 遍历参数值
     */
    public void forEach(@NonNull BiConsumer<String, Writer> consumer) {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        items.forEach(item -> consumer.accept(item.filePath, item.writer));
    }

    void add(String filePath, Writer writer) {
        if (StringUtils.isNotBlank(filePath) && writer != null) {
            items.add(new Item(filePath, writer));
            return;
        }
        IoUtil.close(writer);
    }

    /**
     * 关闭所有的流
     */
    public void closeAllWriter() {
        if (CollectionUtils.isEmpty(items)) {
            return;
        }
        items.forEach(item -> IoUtil.close(item.writer));
    }

    @RequiredArgsConstructor
    private static class Item {
        /**
         * 文件路径
         */
        @NonNull
        private final String filePath;
        /**
         * 文件写入流
         */
        @NonNull
        private final Writer writer;
    }
}
