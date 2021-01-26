package com.rh.note.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.LineHandler;
import com.rh.note.common.BaseFileConfig;
import com.rh.note.common.IReadTitleLine;
import com.rh.note.line.ProxyTitleLine;
import com.rh.note.line.TitleLine;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.TitleSyntax;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * adoc标题文件
 */
@RequiredArgsConstructor
public class AdocTitleFile extends BaseFileConfig {
    /**
     * 文件相对路径
     */
    @NonNull
    private String filePath;
    /**
     * 标题
     */
    @NonNull
    private List<IReadTitleLine> titleLines;
    /**
     * 子文件
     */
    private List<AdocTitleFile> childFiles;

    public @Nullable AdocTitleFile init() {
        if (!FileUtil.isFile(getAbsolutePath()) || "adoc".equalsIgnoreCase(FileUtil.extName(getAbsolutePath()))) {
            return null;
        }
        return this;
    }

    /**
     * 获得文件绝对路径
     * todo twoLevel和content的相对路径需要额外处理
     */
    public @NotNull String getAbsolutePath() {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        String proPath = getProPath();
        if (StringUtils.isBlank(proPath)) {
            return null;
        }
        return proPath + filePath;
    }

    /**
     * 读取标题
     */
    public void readTitle() {
        LineHandler handler = (String line) -> {
            TitleSyntax titleSyntax = new TitleSyntax().init(line);
            if (titleSyntax != null) {
                TitleLine titleLine = new TitleLine().setTitleSyntax(titleSyntax);
                titleLines.add(titleLine);
                return;
            }
            IncludeSyntax includeSyntax = new IncludeSyntax().init(line);
            if (includeSyntax != null) {
                List<IReadTitleLine> childTitles = new ArrayList<>();
                ProxyTitleLine proxyTitleLine = new ProxyTitleLine().setChildTitles(childTitles);
                titleLines.add(proxyTitleLine);
                childFiles.add(new AdocTitleFile(includeSyntax.getIncludePath(), childTitles));
            }
        };
        FileUtil.readUtf8Lines(new File(getAbsolutePath()), handler);
    }

    /**
     * 加载子文件
     */
    public void loadChildrenFile() {
    }

    /**
     * 建立标题关系
     */
    public void addTitleRelation() {
    }

    /**
     * 获得根标题
     */
    public @Nullable TitleLine getRootTitle() {
    }
}
