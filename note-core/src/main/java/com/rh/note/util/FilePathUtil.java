package com.rh.note.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.rh.note.constants.RegexConstants;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件路径工具类
 */
public class FilePathUtil {
    /**
     * 路径修复
     * win路径转linux
     * win分隔符和重复分隔符替换
     * 退回路径消除
     */
    public static @Nullable String normalize(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        String tempStr = filePath;
        // win路径转linux
        Matcher matcherDriveLetter = Pattern.compile("^[a-zA-Z]:(.*)$").matcher(tempStr);
        if (matcherDriveLetter.find()) {
            tempStr = matcherDriveLetter.group(1);
        }
        // win分隔符和重复分隔符替换
        tempStr = tempStr.replaceAll("\\\\", "/");
        tempStr = tempStr.replaceAll("/{2,}", "/");
        // 记录是否为绝对路径
        boolean isAbsolutePath = tempStr.startsWith("/");
        // 退回路径消除
        Matcher matcherReturnPath = Pattern.compile("[^/.]+/\\.\\.").matcher(tempStr);
        while (matcherReturnPath.find()) {
            tempStr = matcherReturnPath.replaceAll("");
            tempStr = tempStr.replaceAll("/{2,}", "/");
            matcherReturnPath = Pattern.compile("[^/.]+/\\.\\.").matcher(tempStr);
        }
        // 删除相对路径意外产生的 /
        if (!isAbsolutePath && tempStr.startsWith("/")) {
            tempStr = tempStr.substring(1);
        }
        return tempStr;
    }

    /**
     * include指向路径 转 项目路径<br/>
     * 只用于文件间的路径转换
     * 如果includePath为绝对路径, 则返回null
     */
    public static @Nullable String includePath2ProFilePath(String currentFileProPath, String includePath) {
        if (StringUtils.isBlank(currentFileProPath) || StringUtils.isBlank(includePath)
                || !currentFileProPath.matches(RegexConstants.file_path_regex)
                || !includePath.matches(RegexConstants.file_path_regex)
        ) {
            return null;
        }
        if (FileUtil.isAbsolutePath(includePath)) {
            return null;
        }
        // 获得当前目录
        String parentPath = Optional.of(currentFileProPath.lastIndexOf("/"))
                .map(index -> Math.max(index, 0))
                .map(index -> currentFileProPath.substring(0, index))
                .filter(StringUtils::isNotBlank)
                .map(path -> path + "/")
                .orElse("");
        return normalize(parentPath + includePath);
    }

    /**
     * 文件绝对路径 转 项目的相对路径
     */
    public static @Nullable String absolutePath2ProFilePath(String targetAbsolutePath, String proPath) {
        if (!FileUtil.isAbsolutePath(targetAbsolutePath) || !FileUtil.isAbsolutePath(proPath)) {
            return null;
        }
        int count = StrUtil.count(proPath, '/');
        boolean isEndWithKong = proPath.endsWith("/");
        int level = isEndWithKong ? count - 1 : count;
        if (level < 0) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        // 退至根目录
        for (int i = 0; i < level; i++) {
            result.append("../");
        }
        result.setLength(result.length() - 1);
        result.append(targetAbsolutePath);
        return normalize(result.toString());
    }
}
