package com.rh.note.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.rh.note.ao.CreateProjectAO;
import com.rh.note.constant.AdocFilePathEnum;
import com.rh.note.entity.adoc.impl.AdocProject;
import com.rh.note.entity.adoc.impl.AdocReadMe;
import com.rh.note.service.IFileService;
import com.rh.note.util.FileUtil;
import lombok.NonNull;
import org.apache.commons.lang3.mutable.MutableInt;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 文件服务
 */
public class FileServiceImpl implements IFileService {

    private FileUtil fileUtil = new FileUtil();

    @Override
    public void createProject(@NonNull AdocProject adocProject) {
        // 创建项目结构目录
        List<String> directorPaths = adocProject.getProjectStructureDirectorPaths();
        fileUtil.createFileDirectorys(directorPaths);
        // 创建项目文件
        List<String> filePaths = adocProject.getAllProjectFilePaths();
        fileUtil.createFiles(filePaths);
    }

    @Override
    public boolean checkProjectExist(@NonNull CreateProjectAO ao) {
        String path = ao.generateAbsolutePath();
        File file = new File(path);
        //项目目录存在
        if (!file.exists() || file.isFile()) {
            return false;
        }
        //readme.adoc文件存在
        String[] list = file.list();
        if (list.length < 1) {
            return false;
        }

        return Arrays.stream(list).anyMatch(AdocReadMe.readMeFileName::equalsIgnoreCase);
    }

    @Override
    public AdocProject readProjectFile(@NonNull CreateProjectAO ao) {
        String path = ao.generateAbsolutePath();
        BiConsumer<File, String> consumer = (file, line) -> {
        };
        this.readFiles(path, consumer);
        // todo
        System.err.println("TODO 读取项目文件为 AdocProject 对象");
        return null;
    }

    /**
     * 读取目标文件
     *
     * readme文件
     * 标题文件 adoc/two_level
     * 内容文件 adoc/content
     */
    private void readFiles(@NonNull String path, @NonNull BiConsumer<File, String> consumer) {
        MutableInt readNum = new MutableInt(0);
        List<String> allAdocFiles = new ArrayList<>();
        int pageSize = 7;

        //readMe
        allAdocFiles.add(AdocFilePathEnum.read_me_file.getAdocFilePath(path));
        //twoLevel
        List<String> twoLevelFilePaths = this.getTwoLevelFiles(path);
        allAdocFiles.addAll(twoLevelFilePaths);
        //content
    }

    /**
     * 得到二级目录路径
     */
    private List<String> getTwoLevelFiles(@NonNull String path) {
        File file = new File(AdocFilePathEnum.two_level_folder.getAdocFilePath(path));
        if (!file.exists()) {
            return Collections.emptyList();
        }

        String[] filePaths = file.list();
        return ArrayUtil.isNotEmpty(filePaths) ? Arrays.asList(filePaths) : Collections.emptyList();
    }

}
