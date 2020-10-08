package com.rh.note.api;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.ProBeanPath;
import com.rh.note.path.ReadMeBeanPath;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

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

    public @Nullable TitleLine readAllTitleByProjectPath() {
        AdocFile adocFile = AdocFile.getInstance(new ReadMeBeanPath().getFilePath());
        return adocFile != null ? adocFile.getRootTitle() : null;
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

    /**
     * 获得文件, 通过项目路径
     */
    public @Nullable AdocFileBeanPath getFileByProPath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        return AdocFileBeanPath.create(filePath);
    }
}
