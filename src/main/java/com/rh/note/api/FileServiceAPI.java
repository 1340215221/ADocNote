package com.rh.note.api;

import com.rh.note.file.AdocFile;
import com.rh.note.file.ConfigFile;
import com.rh.note.file.ProjectDirectory;
import com.rh.note.file.ReadMeFile;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.service.FileService;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * 文件操作api
 */
@Setter
public class FileServiceAPI {

    private FileService fileService;

    /**
     * 读取打开记录
     */
    public RecentlyOpenedRecordVO[] writeOpenRecord() {
        //todo
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
    public TitleGrammar findAllTitle() {
        TitleGrammar root = new TitleGrammar();
        root.setName("java-note");
        root.setFilePath("root");
        TitleGrammar t1 = new TitleGrammar();
        t1.setName("java基础");
        t1.setFilePath("adoc/twoLevel/t1");
        root.addChildrenTitle(t1);
        TitleGrammar t2 = new TitleGrammar();
        t2.setName("java框架");
        t2.setFilePath("adoc/twoLevel/t2");
        root.addChildrenTitle(t2);

        // 依次读取readme twoLevel content文件内容
        ReadMeFile readMe = new ReadMeFile().init();
        // 生成嵌套的标题对象
        new AdocFile().title().include().init(readMe);
        return readMe.getRootTitle();

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
    public ConfigFile findConfigFile() {
        //todo
        return new ConfigFile().setFilePath("/home/hang/adoc/");
    }

    /**
     * 创建adoc文件
     */
    public void createFile(AdocFile adocFile) {
        //todo
    }

    /**
     * 初始化readme文件
     */
    public void initReadMeFile() {
        // todo
        // 获得文件路径
        // 检查是否存在
        // 检查内容,引用config,初始化目录
    }

    /**
     * 初始化config文件
     */
    public void initConfigFile() {
        //todo
        // 获得文件路径
        // 检查是否存在
        // 检查内容,是否配置项目路径变量
    }

    /**
     * 添加项目打开记录
     */
    public void addOpenProjectRecord(String projectPath) {
        //todo
    }

    /**
     * 设置项目地址
     */
    public void setProjectPath(String projectPath) {
        new ProjectDirectory().setAbsolutePath(projectPath);
    }
}
