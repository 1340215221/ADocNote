package com.rh.note.ao;

import com.rh.note.config.FrameLaunchConfig;
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
        FrameLaunchConfig config = new FrameLaunchConfig(proPath);
        return new LoadContextAO(FrameCategoryEnum.WORK)
                .setContextConfig(config);
    }
}
