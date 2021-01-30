package com.rh.note.api;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.ClickedProjectListAO;
import com.rh.note.ao.SaveTextPaneFileByFilePathAO;
import com.rh.note.ao.TextPaneFileWritersAO;
import com.rh.note.file.ReadMeTitleFile;
import com.rh.note.line.TitleLine;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.Reader;

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

    /**
     * 获得文件写入流, 通过文件路径
     */
    public @Nullable TextPaneFileWritersAO getWriterByFilePath(SaveTextPaneFileByFilePathAO ao) {
        if (ao == null || ao.checkMissRequiredParams()) {
            return null;
        }
        return ao.forEach((String absolutePath) -> {
            if (!FileUtil.isFile(absolutePath)) {
                return null;
            }
            return FileUtil.getWriter(absolutePath, CharsetUtil.UTF_8, false);
        });
    }

    /**
     * 读取文件内容
     */
    public @Nullable Reader readFileContent(String absolutePath) {
        if (StringUtils.isBlank(absolutePath) || !FileUtil.isFile(absolutePath)) {
            return null;
        }
        return FileUtil.getReader(absolutePath, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 判断是adoc文件
     */
    public boolean checkIsAdocFile(String absolutePath) {
        return StringUtils.isNotBlank(absolutePath) && FileUtil.isFile(absolutePath)
                && "adoc".equalsIgnoreCase(FileUtil.extName(absolutePath));
    }
}
