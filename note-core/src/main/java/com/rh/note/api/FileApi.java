package com.rh.note.api;

import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.ClickedProjectListAO;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

/**
 * 文件操作 接口
 */
@Component
public class FileApi {
    /**
     * 校验是否为adoc项目
     */
    public @Nullable ClickedProjectListAO checkIsAdocProject(CheckIsAdocProjectAO ao) {
        if (ao == null) {
            return null;
        }
        File file = new File(ao.getProPath());
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        String[] childFileArr = file.list();
        if (ArrayUtils.isEmpty(childFileArr)) {
            return null;
        }
        if (Arrays.stream(childFileArr).noneMatch(filePath -> filePath.endsWith("README.adoc"))) {
            return null;
        }
        return ao.copyTo();
    }
}
