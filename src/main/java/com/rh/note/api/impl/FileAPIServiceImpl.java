package com.rh.note.api.impl;

import com.rh.note.ao.CreateProjectAO;
import com.rh.note.api.IFileAPIService;
import com.rh.note.constant.ErrorCodeEnum;
import com.rh.note.entity.adoc.AdocTitile;
import com.rh.note.entity.adoc.impl.AdocProject;
import com.rh.note.exception.AdocException;
import com.rh.note.service.IAdocService;
import com.rh.note.service.IFileService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 文件操作api
 */
@RequiredArgsConstructor
public class FileAPIServiceImpl implements IFileAPIService {

    @NonNull
    private IFileService fileService;
    @NonNull
    private IAdocService adocService;

    /**
     * 创建项目
     */
    @Override
    public AdocProject createProject(@NonNull CreateProjectAO ao) {
        //判断项目是否存在
        if (fileService.checkProjectExist(ao)) {
            //存在, 解析项目
            return fileService.readProjectFile(ao);
        }

        //不存在, 初始化项目
        AdocProject adocProject = adocService.createProject(ao);
        fileService.createProject(adocProject);
        return adocProject;
    }

    /**
     * 打开一个adoc文件
     */
    @Override
    public File openAdocFile(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        return new File(filePath);
    }

    @Override
    public List<AdocTitile> queryProjectList(String path) {
        if (StringUtils.isBlank(path)) {
            return Collections.emptyList();
        }

        List<AdocTitile> adocTitiles = new ArrayList<>();
        // 读取read.me文件
        File readMeFile = this.getReadMeFile(path);
        if (readMeFile == null) {
            return adocTitiles;
        }

        try {
            this.forEachFileLineContent(readMeFile, adocTitiles, (lineNumer, lineContent, parentTitle) ->
                this.handleAdocContentLine(path, readMeFile, adocTitiles, lineNumer, lineContent, parentTitle)
            );
        } catch (Exception e) {
            throw new AdocException(ErrorCodeEnum.file_read_failed);
        }
        return adocTitiles.stream().sorted(Comparator.comparing(AdocTitile::getLevel)).findFirst().map(Collections::singletonList).get();
    }

    private AdocTitile handleAdocContentLine(String projectPath, File readMeFile, List<AdocTitile> adocTitiles,
                                             Integer lineNumer, String lineContent, MutableObject<AdocTitile> parentTitle) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }

        // 是标题
        Matcher matcher = Pattern.compile("^(=+)\\s(\\S+)\\s*$").matcher(lineContent);
        if (!matcher.find()) {
            // 是include语句
            Matcher matcher2 = Pattern.compile("^\\s*include::([^\\.]+)\\.(\\S+)\\[\\S*\\]$").matcher(lineContent);
            if (!matcher2.find()) {
                return null;
            }
            // 此处没有处理adoc变量
            String filePath = matcher2.group(1);
            String fileSuffix = matcher2.group(2);
            if (!"adoc".equals(fileSuffix)) {
                return null;
            }

            File include = new File(filePath + ".adoc");
            try {
                this.forEachFileLineContent(include, adocTitiles, (ln, lc, pt) ->
                        this.handleAdocContentLine(projectPath, include, adocTitiles, ln, lc, parentTitle)
                );
            } catch (Exception e) {
                throw new AdocException(ErrorCodeEnum.file_read_failed);
            }
            return null;
        }

        String titleLevel = matcher.group(1);
        String title = matcher.group(2);
        AdocTitile adocTitile = new AdocTitile();
        adocTitile.setId(this.getPathInProject(projectPath, readMeFile) + "-" + titleLevel.length() + "-" + title);
        adocTitile.setDisplayName(title);
        adocTitile.setLineNumber(lineNumer);
        adocTitile.setLevel(titleLevel.length());
        adocTitile.setPath(readMeFile.getPath());
        AdocTitile parentTitleValue = parentTitle.getValue();
        // 组装树结构
        this.buildParentTitle(adocTitile, parentTitleValue);
        parentTitle.setValue(adocTitile);
        return adocTitile;
    }

    /**
     * 组装父结构
     */
    private void buildParentTitle(@NonNull AdocTitile adocTitile, AdocTitile parentTitleValue) {
        if (parentTitleValue == null || adocTitile.getLevel() < 2) {
            return;
        }

        Function<AdocTitile, AdocTitile> function = parentAT -> {
            if (parentAT == null) {
                return null;
            }

            if (parentAT.getLevel() < adocTitile.getLevel()) {
                adocTitile.setParentTitle(parentAT);
                parentAT.addChildrenTitle(adocTitile);
                return null;
            }

            return parentAT.getParentTitle();
        };

        AdocTitile apply = function.apply(parentTitleValue);
        while (apply != null) {
            apply = function.apply(apply);
        }
    }

    /**
     * 得到文件在项目中的路径
     */
    private String getPathInProject(@NonNull String projectPath, @NonNull File file) {
        String path = file.getPath();
        if (StringUtils.isBlank(path)) {
            return null;
        }

        final int index = path.indexOf(projectPath);
        return path.substring(index);
    }

    private File getReadMeFile(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }

        if (path.indexOf(File.separator) != path.length()) {
            path = path + File.separatorChar;
        }

        File file = new File(path + "README.adoc");
        if (!file.exists() || file.isDirectory()) {
            return null;
        }

        return file;
    }

    /**
     * 读取文件每一行
     */
    private void forEachFileLineContent(@NonNull File file, @NonNull List<AdocTitile> adocTitiles,
                                        @NonNull ForEachTitileFunctoin function) throws Exception {
        if (!file.exists() || file.isDirectory()) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)
        ) {
            MutableInt lineNumber = new MutableInt(0);
            MutableObject<AdocTitile> parentTile = new MutableObject<>();
            br.lines().map(line -> {
                lineNumber.increment();
                return function.apply(lineNumber.getValue(), line, parentTile);
            }).filter(Objects::nonNull).collect(Collectors.toCollection(() -> adocTitiles));
        }
    }

    private interface ForEachTitileFunctoin {
        AdocTitile apply(Integer lineNumber, String lineContent, MutableObject<AdocTitile> adocTitile);
    }

}
