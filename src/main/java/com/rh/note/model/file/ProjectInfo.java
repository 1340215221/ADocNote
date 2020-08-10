package com.rh.note.model.file;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目信息
 */
@Data
@NoArgsConstructor
public class ProjectInfo {
    private String absolutePath;
    private String projectName;
    private ReadMe readMe;
    private Config config;
    private TwoLevel twoLevel;
    private Content content;

}
