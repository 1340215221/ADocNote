package com.rh.note.style;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 样式集合
 */
public class StyleList {

    private final List<StyleItem> list = new ArrayList<>();

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

}
