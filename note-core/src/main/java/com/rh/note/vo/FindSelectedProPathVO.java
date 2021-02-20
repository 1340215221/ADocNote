package com.rh.note.vo;

import com.rh.note.ao.CheckIsAdocProjectAO;
import com.rh.note.ao.GitPullAO;
import com.rh.note.ao.LoadContextAO;
import com.rh.note.config.FrameLaunchConfig;
import com.rh.note.constants.FrameCategoryEnum;
import com.rh.note.view.ProListView;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 获得项目列表中被选择的项 结果
 */
@Data
public class FindSelectedProPathVO {
    /**
     * 项目路径
     */
    private String proPath;

    public void copy(ProListView view) {
        if (view == null || StringUtils.isBlank(view.getSelectProPath())) {
            return;
        }
        proPath = view.getSelectProPath();
    }

    public @NotNull CheckIsAdocProjectAO copyToCheckIsAdocProjectAO() {
        CheckIsAdocProjectAO ao = new CheckIsAdocProjectAO();
        ao.setProPath(proPath);
        return ao;
    }

    public @Nullable LoadContextAO copyToLoadContextAO() {
        FrameLaunchConfig config = FrameLaunchConfig.getInstance(proPath);
        if (config == null) {
            return null;
        }
        LoadContextAO ao = new LoadContextAO();
        ao.setContextConfig(config);
        ao.setFrameCategoryEnum(FrameCategoryEnum.WORK);
        return ao;
    }

    public @NotNull GitPullAO copyToGitPullAO() {
        GitPullAO ao = new GitPullAO();
        ao.setAbsolutePath(proPath);
        return ao;
    }
}
