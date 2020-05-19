package com.rh.note.util;

import cn.hutool.core.util.ArrayUtil;
import com.rh.note.constant.ErrorCodeEnum;
import com.rh.note.exception.AdocException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class FileUtil {

    /**
     * 创建目录
     * todo
     */
    public static void createPath(String path) {
        if (StringUtils.isBlank(path)) {
            return;
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        if (file.isFile()) {
            throw new AdocException(ErrorCodeEnum.file_directory_creation_failed);
        }
    }
}
