package com.rh.note.path;

import com.rh.note.base.BeanPath;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * adoc文件地址
 */
@Getter
public abstract class AdocFileBeanPath implements BeanPath {
    /**
     * 文件地址
     */
    private String filePath;

    protected AdocFileBeanPath(@NotNull String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getTitleName() {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        int index = filePath.lastIndexOf("/");
        return filePath.substring(index + 1);
    }

    @Override
    public @NotNull String getBeanPath() {
        return filePath;
    }
}
