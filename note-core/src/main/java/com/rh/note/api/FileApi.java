package com.rh.note.api;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.SaveTextPaneFileByFilePathAO;
import com.rh.note.ao.TextPaneFileWritersAO;
import com.rh.note.collection.ReadTitleLineList;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.CreateDuplicateAdocFileNameException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.StopFindRootTitleException;
import com.rh.note.file.AdocTitleFile;
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
    public boolean checkIsAdocProject(CheckIsAdocProjectAO ao) {
        if (ao == null || ao.checkMissRequiredParams() || !FileUtil.isDirectory(ao.getProPath())) {
            return false;
        }
        File readMeFile = new File(ao.getReadMeFilePath());
        if (!readMeFile.exists() || !readMeFile.isFile()) {
            return false;
        }
        try {
            FileUtil.readUtf8Lines(readMeFile, ao.getLineHandler());
        } catch (StopFindRootTitleException e) {
        }
        return ao.isFind();
    }

    /**
     * 读取项目的根标题
     */
    public @Nullable TitleLine readProjectRootTitle() {
//        ReadMeTitleFile readMe = new ReadMeTitleFile().init();
        AdocTitleFile readMe = new AdocTitleFile("adoc/twoLevel/haha.adoc", new ReadTitleLineList());
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
    public @Nullable Reader readAdocFileContent(String absolutePath) {
        if (StringUtils.isBlank(absolutePath) || !FileUtil.isFile(absolutePath)
                || !"adoc".equalsIgnoreCase(FileUtil.extName(absolutePath))
        ) {
            return null;
        }
        return FileUtil.getReader(absolutePath, CharsetUtil.CHARSET_UTF_8);
    }

    /**
     * 创建adoc文件
     */
    public void createAdocFile(String absolutePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        File file = new File(absolutePath);
        if (file.exists()) {
            throw new CreateDuplicateAdocFileNameException();
        }
        File parent = file.getParentFile();
        if (parent.exists() && !parent.isDirectory()) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_CREATE_ADOC_FILE);
        }
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_CREATE_ADOC_FILE);
        }
    }
}
