package com.rh.note.entity.adoc.impl;

import com.rh.note.constant.AdocFilePathEnum;
import com.rh.note.entity.adoc.AdocFile;
import lombok.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.function.Consumer;

/**
 * adoc项目
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AdocProject {

    private Long id;
    /**
     * 项目绝对路径
     */
    @NonNull
    private String path;

    /**
     * 项目名
     */
    @NonNull
    private String name;

    /**
     * 项目主文件
     */
    private AdocFile mainAdocFile;

    /**
     * 初始化项目信息
     */
    public void initProjectInfo() {
        AdocReadMe readMe = new AdocReadMe();
        readMe.init(name, path);
        mainAdocFile = readMe;
    }

    /**
     * 初始化主文件
     */
    private void initMainAdocFile() {

    }

    /**
     * 获得项目结构目录路径
     */
    public List<String> getProjectStructureDirectorPaths() {
        // 二级标题
        String twoLevel = this.getTwoLevelPath();
        // 内容
        String content = this.getContentPath();
        return Arrays.asList(twoLevel, content);
    }

    /**
     * 得到内容目录路径
     */
    private String getContentPath() {
        return AdocFilePathEnum.content.getAdocFilePath(path);
    }

    /**
     * 得到二级标题路径
     */
    private String getTwoLevelPath() {
        return AdocFilePathEnum.two_level_folder.getAdocFilePath(path);
    }

    /**
     * 获得项目所有文件路径
     */
    public List<String> getAllProjectFilePaths() {
        // todo
        System.err.println("TODO 获得所有项目文件路径");
        return Collections.emptyList();
    }

}
