package com.rh.note.api;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.LineHandler;
import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.ClickedProjectListAO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;

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
        File readMeFile = new File(ao.getReadMeFilePath());
        if (!readMeFile.exists() || !readMeFile.isFile()) {
            return null;
        }
        FileUtil.readUtf8Lines(readMeFile, ao.getLineHandler());
        return ao.copyTo();
    }

    /**
     * 检查项目有根标题
     */
    public void checkHasRootTitle(ClickedProjectListAO ao) {
        if (ao == null || StringUtils.isBlank(ao.getProPath())) {
            return;
        }
        File file = new File(ao.getProPath());
        if (file.exists()) {
        }
    }
}
