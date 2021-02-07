package com.rh.note.vo;

import com.rh.note.util.CurrentAdocProConfigUtil;
import com.rh.note.util.FilePathUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 重命名adoc文件 结果
 */
public class RenameAdocFileVO {
    /**
     * 项目路径
     */
    private final String proPath = CurrentAdocProConfigUtil.getProPath();
    /**
     * 新的文件绝对路径
     */
    private String filePath;

    public void copy(File file) {
        if (file == null || StringUtils.isBlank(proPath)) {
            return;
        }
        filePath = FilePathUtil.absolutePath2ProFilePath(file.getAbsolutePath(), proPath);
    }
}
