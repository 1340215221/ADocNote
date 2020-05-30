package com.rh.note.util;

import com.rh.note.constant.ErrorCodeEnum;
import com.rh.note.exception.AdocException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

public class FileUtil {

    /**
     * 创建文件
     */
    public void createFiles(List<String> paths) {
        if (CollectionUtils.isEmpty(paths)) {
            return;
        }

        paths.forEach(this::createFile);
    }

    /**
     * 创建文件
     */
    public void createFile(String path) {
        if (StringUtils.isBlank(path)) {
            throw new AdocException(ErrorCodeEnum.file_creation_failed);
        }

        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            throw new AdocException(ErrorCodeEnum.file_creation_failed);
        }

        if (file.exists()) {
            return;
        }

        try {
            file.createNewFile();
        } catch (Exception e) {
            throw new AdocException(ErrorCodeEnum.file_creation_failed);
        }
    }

    /**
     * 创建文件目录
     */
    public void createFileDirectory(String path) {
        if (StringUtils.isBlank(path)) {
            throw new AdocException(ErrorCodeEnum.file_directory_creation_failed);
        }

        File file = new File(path);
        if (file.exists() && file.isFile()) {
            throw new AdocException(ErrorCodeEnum.file_directory_creation_failed);
        }

        if (file.exists()) {
            return;
        }

        try {
            file.mkdirs();
        } catch (Exception e) {
            throw new AdocException(ErrorCodeEnum.file_directory_creation_failed);
        }
    }

}
