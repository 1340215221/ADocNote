package com.rh.note.api;

import com.rh.note.model.file.AdocFile;
import com.rh.note.model.file.Config;
import com.rh.note.model.file.Title;
import com.rh.note.service.AdocService;
import com.rh.note.service.FileService;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * 文件操作api
 */
@RequiredArgsConstructor
public class FileAPIService {

    @NonNull
    private FileService fileService;
    @NonNull
    private AdocService adocService;

    /**
     * 读取打开记录
     */
    public RecentlyOpenedRecordVO[] writeOpenRecord() {
        RecentlyOpenedRecordVO vo1 = new RecentlyOpenedRecordVO();
        vo1.setProjectPath("/home/hang/java-note");
        vo1.setProjectName("java笔记");
        RecentlyOpenedRecordVO vo2 = new RecentlyOpenedRecordVO();
        vo2.setProjectPath("/home/hang/life-note");
        vo2.setProjectName("生活笔记");
        RecentlyOpenedRecordVO vo3 = new RecentlyOpenedRecordVO();
        vo3.setProjectPath("/home/hang/work-note");
        vo3.setProjectName("工作笔记");
        return new RecentlyOpenedRecordVO[]{vo1, vo2, vo3};
    }

    /**
     * 查找项目中所有的标题
     */
    public Title findAllTitle() {
        Title root = new Title();
        root.setName("java-note");
        root.setAbsolutePath("root");
        Title t1 = new Title();
        t1.setName("java基础");
        t1.setAbsolutePath("adoc/twoLevel/t1");
        root.addChildrenTitle(t1);
        Title t2 = new Title();
        t2.setName("java框架");
        t2.setAbsolutePath("adoc/twoLevel/t2");
        root.addChildrenTitle(t2);
        return root;
    }

    /**
     * 读取文件内容
     */
    public File readTitleFileContent(String filePath) {
        //todo
        File temp = null;
        BufferedWriter bw = null;
        try {
            temp = File.createTempFile("aaa", "bbb");
            bw = new BufferedWriter(new FileWriter(temp));
            bw.write("请输入内容......");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return temp;
        }
    }

    /**
     * 获取配置文件对象
     */
    public Config findConfigFile() {
        //todo
        return new Config().setProjectPath("/home/hang/adoc/");
    }

    /**
     * 创建adoc文件
     */
    public void createFile(AdocFile adocFile) {
        //todo
    }
}
