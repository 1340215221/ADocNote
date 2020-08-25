package com.rh.note.file;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 项目信息
 */
@Data
@NoArgsConstructor
public class ProjectDirectory {
    private static String absolutePath;
    private String projectName;
    private ReadMeFile readMe;
    private ConfigFile config;
    private TwoLevelFile twoLevel;
    private ContentFile content;

    public void setAbsolutePath(String absolutePath) {
        if (StringUtils.isNotBlank(ProjectDirectory.absolutePath) || StringUtils.isBlank(absolutePath)) {
            return;
        }
        if (!absolutePath.endsWith(File.separator)) {
            absolutePath += File.separator;
        }

        ProjectDirectory.absolutePath = absolutePath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

}
