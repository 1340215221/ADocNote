package com.rh.note.api;

import com.rh.note.ao.ClickedHistoryProjectListAO;
import com.rh.note.ao.ICreateFileAndInitContentAO;
import com.rh.note.ao.IncludePromptAO;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCodeEnum;
import com.rh.note.file.AdocFile;
import com.rh.note.file.JavaProConfig;
import com.rh.note.line.TitleLine;
import com.rh.note.path.AdocFileBeanPath;
import com.rh.note.path.ProBeanPath;
import com.rh.note.path.ReadMeBeanPath;
import com.rh.note.vo.RecentlyOpenedRecordVO;
import com.rh.note.vo.WriterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

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

    /**
     * 创建文件,并初始化文件内容
     */
    public void createFileAndInitContent(ICreateFileAndInitContentAO ao) {
        if (ao == null || StringUtils.isBlank(ao.getAbsolutePath())) {
            return;
        }
        // 生成文件
        File file = new File(ao.getAbsolutePath());
        if (file.exists()) {
            throw new ApplicationException(ErrorCodeEnum.CANNOT_CREATE_A_FILE_WITH_THE_SAME_NAME);
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        try {
            file.createNewFile();
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FILE_CREATION_FAILED, e);
        }
        // 写入文件内容
        if (StringUtils.isBlank(ao.getText())) {
            return;
        }
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(ao.getText());
        } catch (Exception e) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_WRITE_FILE);
        }
    }

    /**
     * 删除文件, 通过文件的项目路径
     */
    public void deleteFileByFilePath(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return;
        }
        String absolutePath = projectPath + filePath;
        File file = new File(absolutePath);
        if (!file.exists()) {
            return;
        }
        if (!file.isFile()) {
            throw new ApplicationException(ErrorCodeEnum.FAILED_TO_DELETE_FILE);
        }
        file.delete();
    }

    /**
     * 文件重命名
     */
    public void renameFile(String filePath, String newName) {
        if (StringUtils.isBlank(filePath) || StringUtils.isBlank(newName)) {
            return;
        }
        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return;
        }
        String absolutePath = projectPath + filePath;
        // 校验原文件
        File file = new File(absolutePath);
        if (!file.exists() || !file.isFile()) {
            throw new ApplicationException(ErrorCodeEnum.FILE_RENAMING_FAILED);
        }
        // 检验新文件路径
        int startIndex = absolutePath.lastIndexOf("/");
        int endIndex = absolutePath.lastIndexOf(".");
        StringBuilder newFilePath = new StringBuilder()
                .append(absolutePath, 0, startIndex + 1)
                .append(newName);
        if (endIndex > -1 && endIndex != absolutePath.length() - 1) {
            newFilePath.append(absolutePath, endIndex, absolutePath.length());
        }
        File newFile = new File(newFilePath.toString());
        if (newFile.exists()) {
            throw new ApplicationException(ErrorCodeEnum.FILE_RENAMING_FAILED);
        }
        // 重命名
        file.renameTo(newFile);
    }

    /**
     * 获得项目列表include提示
     */
    public @Nullable IncludePromptAO getProListIncludePrompt(String proLabel) {
        JavaProConfig config = new JavaProConfig();
        return config.copyToAndFilter(proLabel);
    }

    /**
     * 获得包路径
     */
    public @Nullable IncludePromptAO getPackageListIncludePrompt(String proLabel, String packagePath) {
        if (StringUtils.isBlank(proLabel)) {
            return null;
        }
        String proPath = new JavaProConfig().getProPath(proLabel);
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isNotBlank(projectPath)) {
            return null;
        }
        String lastPackagePath = getLastPackagePath(packagePath);
        String filePath = projectPath + proPath + lastPackagePath;
        File proDirectory = new File(filePath);
        if (!proDirectory.exists() || !proDirectory.isDirectory()) {
            return null;
        }
        String[] fileNameArr = proDirectory.list();
        if (ArrayUtils.isEmpty(fileNameArr)) {
            return null;
        }
        String incompleteContent = getIncompleteContent(packagePath);
        return new IncludePromptAO().copy(fileNameArr, incompleteContent);
    }

    /**
     * 获得完整包路径
     */
    private @NotNull String getLastPackagePath(String packagePath) {
        if (StringUtils.isBlank(packagePath)) {
            return "";
        }
        int index = packagePath.lastIndexOf('.');
        if (index <= 0) {
            return "";
        }
        return packagePath.substring(0, index);
    }

    /**
     * 获得不完整内容
     */
    private @Nullable String getIncompleteContent(String packagePath) {
        if (StringUtils.isBlank(packagePath)) {
            return null;
        }
        int index = packagePath.lastIndexOf('.');
        if (index == 0) {
            return null;
        }
        if (index < 0) {
            return packagePath;
        }
        return packagePath.substring(index);
    }
}
