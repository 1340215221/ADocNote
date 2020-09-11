package com.rh.note.api;

import com.rh.note.vo.RecentlyOpenedRecordVO;

/**
 * 文件服务
 */
public class FileAPIService {
    /**
     * 获取历史项目
     */
    public RecentlyOpenedRecordVO[] readHistoryProject() {
        //todo
        RecentlyOpenedRecordVO vo1 = new RecentlyOpenedRecordVO();
        vo1.setProjectPath("/home/hang/Documents/Java-not");
        vo1.setProjectName("java笔记");
        RecentlyOpenedRecordVO vo2 = new RecentlyOpenedRecordVO();
        vo2.setProjectPath("/InterviewNote");
        vo2.setProjectName("生活笔记");
        RecentlyOpenedRecordVO vo3 = new RecentlyOpenedRecordVO();
        vo3.setProjectPath("/home/hang/work-note");
        vo3.setProjectName("工作笔记");
        return new RecentlyOpenedRecordVO[]{vo1, vo2, vo3};
    }
}
