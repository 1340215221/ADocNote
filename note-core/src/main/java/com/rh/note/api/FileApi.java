package com.rh.note.api;

import cn.hutool.core.io.FileUtil;
import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.file.ReadMeTitleFile;
import com.rh.note.line.TitleLine;
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
     * 读取项目的根标题
     */
    public @Nullable TitleLine readProjectRootTitle() {
        ReadMeTitleFile readMe = new ReadMeTitleFile().init();
        if (readMe == null) {
            return null;
        }
        readMe.readTitle();
        readMe.loadChildrenFile();
        readMe.addTitleRelation();
        return readMe.getRootTitle();
    }
}
