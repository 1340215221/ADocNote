package com.rh.note.api;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.file.AdocFile;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.ProBeanPath;
import com.rh.note.path.ReadMeBeanPath;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import com.rh.note.vo.WriterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 文件服务 操作
 */
@Slf4j
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

    /**
     * 获得文件写入流function, 通过文件地址
     */
    public @Nullable WriterVO openFileOutputStream(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Runnable closeIO = () -> {
                try {
                    fos.close();
                    osw.flush();
                    osw.close();
                } catch (Exception e) {
                    log.error("[关闭文件流失败], filePath={}", filePath, e);
                }
            };
            return new WriterVO(osw, closeIO);
        } catch (Exception e) {
            log.error("[获取文件写入流失败], filePath={}", filePath, e);
            return null;
        }
    }
}
