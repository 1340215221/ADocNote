package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import com.rh.note.common.BaseFileConfig;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.Writer;
import java.util.List;
import java.util.function.Function;

/**
 * 保存编辑区内容,通过文件路径 参数
 */
@RequiredArgsConstructor
public class SaveTextPaneFileByFilePathAO extends BaseFileConfig implements BaseAO {
    /**
     * 文件项目路径
     */
    @NonNull
    private List<String> filePaths;

    @Override
    public boolean checkRequiredParamsError() {
        return CollectionUtils.isEmpty(filePaths);
    }

    /**
     * 遍历参数
     */
    public @Nullable TextPaneFileWritersAO forEach(@NonNull Function<String, Writer> function) {
        if (CollectionUtils.isEmpty(filePaths)) {
            return null;
        }
        TextPaneFileWritersAO ao = new TextPaneFileWritersAO();
        filePaths.forEach(filePath -> {
            String absolutePath = coverAbsolutePath(filePath);
            Writer writer = function.apply(absolutePath);
            ao.add(filePath, writer);
        });
        return ao;
    }

    /**
     * 文件项目路径 转 绝对路径
     */
    public @Nullable String coverAbsolutePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        String proPath = getProPath();
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        return getProPath() + filePath;
    }
}
