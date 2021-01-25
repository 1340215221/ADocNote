package com.rh.note.api;

import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.config.ProjectConfig;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Arrays;

/**
 * 文件操作 接口
 */
@Component
public class FileApi {

    @Autowired
    private ProjectConfig projectConfig;

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

    /**
     * 读取项目的根标题
     */
    public TitleLine readProjectRootTitle() {
        String readMePath = projectConfig.getReadMePath();
        if (StringUtils.isBlank(readMePath)) {
            return null;
        }
        File file = new File(readMePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        new AdocFile()
        return null;
    }
}
