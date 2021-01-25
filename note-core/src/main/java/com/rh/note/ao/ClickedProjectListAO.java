package com.rh.note.ao;

import com.rh.note.config.ProjectConfig;
import com.rh.note.constants.FrameCategoryEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 点击项目列表 参数
 */
@Data
@Accessors(chain = true)
public class ClickedProjectListAO {
    /**
     * 项目路径
     */
    private String proPath;

    public LoadContextAO copyToLoadContextAO() {
        ProjectConfig config = new ProjectConfig(proPath);
        return new LoadContextAO(FrameCategoryEnum.WORK)
                .setContextConfig(config);
    }
}
