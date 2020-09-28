package com.rh.note.api;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.path.ProBeanPath;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import com.rh.note.vo.TitleLineVO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * 文件服务 操作
 */
public class FileServiceApi {
    /**
     * 设置项目路径
     */
    public void setProjectPath(ClickedHistoryProjectListAO ao) {
        if (ao == null || StringUtils.isBlank(ao.getProjectPath())) {
            return;
        }
        new ProBeanPath().setProjectPath(ao.getProjectPath());
    }

    public @Nullable TitleLineVO readAllTitleByProjectPath(ClickedHistoryProjectListAO ao) {
        if (ao == null || StringUtils.isBlank(ao.getProjectPath())) {
            return null;
        }

        //todo
        return new TitleLineVO();
    }

    /**
     * 获得历史打开记录
     */
    public RecentlyOpenedRecordVO[] getHistoryOpenRecords() {
        return new RecentlyOpenedRecordVO[]{
                new RecentlyOpenedRecordVO().setProjectName("java笔记").setProjectPath("/home/hang/Documents/Java-not/"),
                new RecentlyOpenedRecordVO().setProjectName("生活笔记").setProjectPath("/InterviewNote/")
        };
    }
}
