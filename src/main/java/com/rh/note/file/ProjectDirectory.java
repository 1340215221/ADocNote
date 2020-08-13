package com.rh.note.file;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目信息
 */
@Data
@NoArgsConstructor
public class ProjectDirectory {
    private String absolutePath;
    private String projectName;
    private ReadMeFile readMe;
    private ConfigFile config;
    private TwoLevelFile twoLevel;
    private ContentFile content;

}
