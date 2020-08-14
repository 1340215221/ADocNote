package com.rh.note.file;

import com.rh.note.common.IAdocFile;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * readMe文件
 */
@Data
public class ReadMeFile implements IAdocFile {

    public static final String readMeFileName = "README.adoc";
    /**
     * 文件绝对地址
     */
    private String filePath;

    public ReadMeFile init() {
        String projectPath = new ProjectDirectory().getAbsolutePath();
        if (StringUtils.isBlank(projectPath)) {
            return null;
        }
        filePath = projectPath + readMeFileName;
        return this;
    }
}
