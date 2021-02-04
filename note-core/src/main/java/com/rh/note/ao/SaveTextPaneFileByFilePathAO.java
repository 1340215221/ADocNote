package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import com.rh.note.util.CurrentAdocProConfigUtil;
import com.rh.note.vo.GenerateIncludeSyntaxVO;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * 保存编辑区内容,通过文件路径 参数
 */
public class SaveTextPaneFileByFilePathAO implements BaseAO {
    /**
     * 当前adoc项目绝对路径
     */
    private final String proPath = CurrentAdocProConfigUtil.getProPath();
    /**
     * 文件项目路径
     */
    private List<String> filePaths;

    @Override
    public boolean checkMissRequiredParams() {
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
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        return proPath + filePath;
    }

    public void copy(List<String> filPaths) {
        if (CollectionUtils.isEmpty(filPaths)) {
            return;
        }
        this.filePaths = filPaths;
    }

    public void copy(GenerateIncludeSyntaxVO vo) {
        if (vo == null) {
            return;
        }
        filePaths = new ArrayList<>();
        if (StringUtils.isNotBlank(vo.getFilePath())) {
            filePaths.add(vo.getFilePath());
        }
        String targetFilePath = vo.getTargetFilePath();
        if (StringUtils.isNotBlank(targetFilePath)) {
            filePaths.add(targetFilePath);
        }
    }
}
