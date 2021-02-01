package com.rh.note.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.lang.mutable.MutableInt;
import com.rh.note.collection.ReadTitleLineList;
import com.rh.note.common.BaseFileConfig;
import com.rh.note.common.IReadTitleLine;
import com.rh.note.common.ReadTitleLineImpl;
import com.rh.note.constants.FilePathEnum;
import com.rh.note.line.EmptyReadTitleLine;
import com.rh.note.line.ProxyTitleLine;
import com.rh.note.line.TitleLine;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.TitleSyntax;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
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
    private ReadTitleLineList titleLines;
    /**
     * 子文件
     */
    private final List<AdocTitleFile> childFiles = new ArrayList<>();

    public @Nullable AdocTitleFile init() {
        if (!FileUtil.isFile(getAbsolutePath()) || !"adoc".equalsIgnoreCase(FileUtil.extName(getAbsolutePath()))) {
            return null;
        }
        return this;
    }

    /**
     * 获得文件绝对路径
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
        MutableInt lineNumber = new MutableInt(0);
        LineHandler handler = (String line) -> {
            TitleSyntax titleSyntax = new TitleSyntax().init(line);
            if (titleSyntax != null) {
                TitleBeanPath beanPath = new TitleBeanPath()
                        .setFilePath(filePath)
                        .setLineNumber(lineNumber.get());
                TitleLine titleLine = new TitleLine()
                        .setBeanPath(beanPath)
                        .setTitleSyntax(titleSyntax);
                titleLines.add(titleLine);
                return;
            }
            IncludeSyntax includeSyntax = new IncludeSyntax().init(line);
            if (includeSyntax != null) {
                ReadTitleLineList childTitles = new ReadTitleLineList();
                ProxyTitleLine proxyTitleLine = new ProxyTitleLine()
                        .setLineNumber(lineNumber.get())
                        .setChildTitles(childTitles);
                titleLines.add(proxyTitleLine);
                String includeToPath = FilePathEnum.includePath2ProPath(filePath, includeSyntax.getIncludePath());
                childFiles.add(new AdocTitleFile(includeToPath, childTitles));
            }
        };
        FileUtil.readUtf8Lines(new File(getAbsolutePath()), handler);
    }

    /**
     * 加载子文件
     */
    public void loadChildrenFile() {
        if (CollectionUtils.isEmpty(childFiles)) {
            return;
        }
        childFiles.forEach(childFile -> {
            AdocTitleFile file = childFile.init();
            if (file == null) {
                return;
            }
            file.readTitle();
            file.loadChildrenFile();
            file.addTitleRelation();
        });
    }

    /**
     * 建立标题关系
     */
    public void addTitleRelation() {
        if (CollectionUtils.isEmpty(titleLines)) {
            return;
        }
        titleLines.stream()
                .sorted(Comparator.comparing(IReadTitleLine::getLineNumber))
                .reduce((a, b) -> {
                    // title, title
                    if (a instanceof ReadTitleLineImpl && b instanceof ReadTitleLineImpl) {
                        ReadTitleLineImpl parent = ((ReadTitleLineImpl) a).findParentOf((ReadTitleLineImpl) b);
                        if (parent == null) {
                            return a;
                        }
                        parent.relationChild((ReadTitleLineImpl) b);
                        return b;
                    }
                    // title, include
                    if (a instanceof ReadTitleLineImpl && b instanceof ProxyTitleLine) {
                        ReadTitleLineImpl targetTitle = ((ProxyTitleLine) b).getTargetTitle();
                        ReadTitleLineImpl parent = ((ReadTitleLineImpl) a).findParentOf(targetTitle);
                        if (parent == null) {
                            return a;
                        }
                        parent.relationChild(targetTitle);
                        return a;
                    }
                    // include, title
                    if (a instanceof ProxyTitleLine && b instanceof ReadTitleLineImpl) {
                        ReadTitleLineImpl targetTitle = ((ProxyTitleLine) a).getTargetTitle();
                        if (targetTitle == null) {
                            return b;
                        }
                        ReadTitleLineImpl parent = targetTitle.findParentOf((ReadTitleLineImpl) b);
                        if (parent == null) {
                            return targetTitle.getParentTitle() != null ? targetTitle.getParentTitle() : b;
                        }
                        parent.relationChild((ReadTitleLineImpl) b);
                        return b;
                    }
                    // include, include
                    if (a instanceof ProxyTitleLine && b instanceof ProxyTitleLine) {
                        ReadTitleLineImpl targetTitleA = ((ProxyTitleLine) a).getTargetTitle();
                        ReadTitleLineImpl targetTitleB = ((ProxyTitleLine) b).getTargetTitle();
                        if (targetTitleA == null || targetTitleA.getParentTitle() == null) {
                            return new EmptyReadTitleLine();
                        }
                        if (targetTitleB == null) {
                            return targetTitleA.getParentTitle();
                        }
                        ReadTitleLineImpl parent = targetTitleA.findParentOf(targetTitleB);
                        if (parent == null) {
                            return targetTitleA.getParentTitle();
                        }
                        parent.relationChild(targetTitleB);
                        return parent;
                    }
                    // null, title
                    if (a instanceof EmptyReadTitleLine && b instanceof ReadTitleLineImpl) {
                        return b;
                    }
                    // null, include
                    // other
                    return new EmptyReadTitleLine();
                });
    }

    /**
     * 获得根标题
     */
    public @Nullable TitleLine getRootTitle() {
        return titleLines.getRootTitleLine();
    }
}
