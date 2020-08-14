package com.rh.note.file;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isBlank(absolutePath)) {
            ProjectDirectory.absolutePath = absolutePath;
        }
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

}
