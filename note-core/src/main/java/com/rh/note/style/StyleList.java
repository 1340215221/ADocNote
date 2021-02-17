package com.rh.note.style;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 样式集合
 */
public class StyleList {

    private final List<StyleItem> list;

    public StyleList() {
        list = new ArrayList<>();
    }

    public void add(StyleItem item) {
        if (item != null) {
            list.add(item);
        }
    }

    public boolean isEmpty() {
        return list.size() < 1;
    }

    public void forEach(@NonNull Consumer<StyleItem> consumer) {
        if (!isEmpty()) {
            list.forEach(consumer);
        }
    }

    public void addAll(StyleList list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        this.list.addAll(list.list);
    }

    /**
     * 获得启用的样式
     * todo
     */
    public @NotNull StyleList getEnableStyle() {
        // 过滤启用的集合
        StyleList styleList = new StyleList();
        styleList.list.addAll(list);
        // 删除启用的
        list.clear();
        return styleList;
    }
}
