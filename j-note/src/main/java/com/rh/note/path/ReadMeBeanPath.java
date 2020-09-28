package com.rh.note.path;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * read_me地址
 */
public final class ReadMeBeanPath extends AdocFileBeanPath {

    protected ReadMeBeanPath() {
        super("README.adoc");
    }

    public static @Nullable ReadMeBeanPath create(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        return new ReadMeBeanPath();
    }

}
