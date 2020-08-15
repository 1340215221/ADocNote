package com.rh.note.api;

import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.AdocException;
import com.rh.note.file.AdocFile;
import com.rh.note.file.ProjectDirectory;
import com.rh.note.file.ReadMeFile;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * 文件操作api
 */
@Setter
public class FileServiceAPI {

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
        // 依次读取readme twoLevel content文件内容
        ReadMeFile readMe = new ReadMeFile().init();
        // 生成嵌套的标题对象
        new AdocFile()
                .title()
                .include()
                .init(readMe);
        return readMe.getRootTitle();
    }

    /**
     * 读取文件内容
     */
    public File readTitleFileContent(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        return file;
    }

    /**
     * 创建adoc文件
     */
    public void createFile(AdocFile adocFile) {
        if (adocFile == null || StringUtils.isBlank(adocFile.getFilePath())) {
            return;
        }
        File file = new File(adocFile.getAbsolutePath());
        if (file.exists()) {
            throw new AdocException(ErrorMessage.file_creation_failed);
        }
        File parentFile = file.getParentFile();
        if (parentFile.exists() && !parentFile.isDirectory()) {
            throw new AdocException(ErrorMessage.file_creation_failed);
        }
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        FileWriter fw = null;
        try {
            file.createNewFile();
            fw = new FileWriter(file);
            fw.write(adocFile.toString());
        } catch (Exception e) {
            throw new AdocException(ErrorMessage.file_creation_failed);
        }finally {
            IOUtils.closeQuietly(fw);
        }
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

    /**
     * 通过文件路径获得输出流
     */
    public Writer openFileOutputStream(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        File file = new File(new ProjectDirectory().getAbsolutePath() + filePath);
        if (!file.exists() || !file.isFile()) {
            return null;
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            return new OutputStreamWriter(fos);
        } catch (Exception e) {
            throw new AdocException(ErrorMessage.file_read_failed);
        }
    }
}
