package com.rh.note.api;

import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.exception.NoteException;
import com.rh.note.file.AdocFile;
import com.rh.note.file.ProjectDirectory;
import com.rh.note.file.ReadMeFile;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        vo1.setProjectPath("/home/hang/Documents/Java-not");
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
            throw new NoteException(ErrorMessage.file_creation_failed);
        }
        File parentFile = file.getParentFile();
        if (parentFile.exists() && !parentFile.isDirectory()) {
            throw new NoteException(ErrorMessage.file_creation_failed);
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
            throw new NoteException(ErrorMessage.file_creation_failed);
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
            throw new NoteException(ErrorMessage.file_read_failed);
        }
    }

    /**
     * 修改文件根标题名
     */
    public void changeRootTitleOfIncludeFile(String absolutePath, String newName) {
        if (StringUtils.isBlank(absolutePath) || StringUtils.isBlank(newName)) {
            return;
        }
        File file = new File(absolutePath);
        if (!file.exists() || !file.isFile()) {
            return;
        }
        int start = absolutePath.lastIndexOf(File.separator) + 1;
        int end = absolutePath.lastIndexOf(".");
        String fileName = absolutePath.substring(start, end);
        String fileContent = null;
        try (FileReader fileReader = new FileReader(file);BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            fileContent = bufferedReader.lines().map(lineContent -> {
                if (StringUtils.isBlank(lineContent)) {
                    return lineContent;
                }
                Matcher matcher = Pattern.compile("^(=+)\\s+" + fileName + "\\s*$").matcher(lineContent);
                if (matcher.find()) {
                    String level = matcher.group(1);
                    return level + " " + newName;
                }
                return lineContent;
            }).collect(Collectors.joining("\n"));
            if (StringUtils.isBlank(fileContent)) {
                return;
            }
        } catch (Exception e) {
            throw new NoteException(ErrorCodeEnum.FILE_READ_FAILED, e);
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(fileContent);
        } catch (Exception e) {
            throw new NoteException(ErrorCodeEnum.FILE_WRITE_FAILED, e);
        }
    }

    /**
     * 修改文件名
     */
    public void changeFileName(String filePath, String newName) {
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(newName)) {
            return;
        }
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return;
        }
        //todo 这里没考虑没有文件后缀的情况
        int start = filePath.lastIndexOf(File.separator) + 1;
        int end = filePath.lastIndexOf(".");
        String newFilePath = filePath.substring(0, start) + newName + filePath.substring(end, filePath.length());
        // todo 这里没有处理文件已存在的情况
        File newFile = new File(newFilePath);
        file.renameTo(newFile);
    }

    /**
     * 判断文件是否存在
     */
    public boolean checkFileIsExists(String absolutePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return true;
        }
        File file = new File(absolutePath);
        return file.exists();
    }

    /**
     *删除include行和文件
     */
    public void deleteByAbsolutePath(String absolutePath) {
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        final File file = new File(absolutePath);
        if (!file.exists() || !file.isFile()) {
            return;
        }
        file.delete();
    }
}
