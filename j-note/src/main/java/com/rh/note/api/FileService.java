package com.rh.note.api;

import com.rh.note.file.ProjectDirectory;
import com.rh.note.file.ReadMe;
import com.rh.note.line.TitleLine;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 文件服务
 */
public class FileService {
    /**
     * 获取历史项目
     */
    public RecentlyOpenedRecordVO[] readHistoryProject() {
        //todo
        RecentlyOpenedRecordVO vo1 = new RecentlyOpenedRecordVO();
        vo1.setProjectPath("/home/hang/Documents/Java-not/");
        vo1.setProjectName("java笔记");
        RecentlyOpenedRecordVO vo2 = new RecentlyOpenedRecordVO();
        vo2.setProjectPath("/InterviewNote/");
        vo2.setProjectName("生活笔记");
        RecentlyOpenedRecordVO vo3 = new RecentlyOpenedRecordVO();
        vo3.setProjectPath("/home/hang/work-note/");
        vo3.setProjectName("工作笔记");
        return new RecentlyOpenedRecordVO[]{vo1, vo2, vo3};
    }

    /**
     * 读取所有标题, 返回根标题
     */
    public TitleLine readAllTitle() {
        ReadMe readMe = new ReadMe().init();
        if (readMe == null) {
            return null;
        }
        return readMe.getRootTitle();
    }

    /**
     * 选择项目,通过项目地址
     */
    public void selectProject(String projectPath) {
        new ProjectDirectory().setProjectPath(projectPath);
    }

    /**
     * 获得文件对象
     */
    public File getFileByPath(String absolutePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return null;
        }
        return new File(absolutePath);
    }
}
