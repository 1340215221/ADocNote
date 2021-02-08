package com.rh.note.path;

import cn.hutool.core.io.FileUtil;
import com.rh.note.common.IArgsConstructorBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 文件对象路径
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileBeanPath implements IArgsConstructorBean {
    /**
     * 文件项目路径
     */
    @NonNull
    private String filePath;

    public static @Nullable FileBeanPath getInstance(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        FileBeanPath beanPath = new FileBeanPath();
        beanPath.filePath = filePath;
        return beanPath;
    }

    @Override
    public @NotNull String[] getBeanNameArgs() {
        return new String[]{filePath};
    }

    /**
     * 获得文件名
     */
    public @NotNull String getFileName() {
        return FileUtil.getName(filePath);
    }
}
