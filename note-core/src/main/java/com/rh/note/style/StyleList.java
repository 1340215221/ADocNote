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

    public @NotNull StyleList addAll(StyleList list) {
        if (list == null || list.isEmpty()) {
            return this;
        }
        if (isEmpty()) {
            return list;
        }
        this.list.addAll(list.list);
        return this;
    }

}
