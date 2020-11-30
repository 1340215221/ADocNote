package com.rh.note.path;

import com.rh.note.base.BeanPath;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

/**
 * adoc文件地址
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AdocFileBeanPath implements BeanPath {
    /**
     * 文件地址
     */
    @Getter
    @NonNull
    private String filePath;

    public static @Nullable AdocFileBeanPath create(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return null;
        }
        File file = new File(projectPath + filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        return new AdocFileBeanPath(filePath);
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

    public @NotNull File getFile() {
        String projectPath = new ProBeanPath().getProjectPath();
        return new File(projectPath + filePath);
    }

    /**
     * 获取文件的绝对路径
     */
    public String getAbsolutePath() {
        String projectPath = new ProBeanPath().getProjectPath();
        return projectPath + filePath;
    }
}
