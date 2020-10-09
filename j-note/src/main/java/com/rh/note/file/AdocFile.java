package com.rh.note.file;

import com.rh.note.base.BaseLine;
import com.rh.note.exception.RequestParamsValidException;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TitleLine;
import com.rh.note.path.ProBeanPath;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.TitleSyntax;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * adoc文件
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AdocFile {

    @NonNull
    @Getter
    private String filePath;
    /**
     * 是否解析子文件
     */
    private boolean parsingChildrenFile = true;
    /**
     * 标题内容
     */
    private final List<TitleLine> titleLines = new ArrayList<>();
    /**
     * include内容
     */
    private final List<IncludeLine> includeLines = new ArrayList<>();

    public static @Nullable AdocFile getInstance(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        return new AdocFile(filePath).init();
    }

    public static @Nullable AdocFile getInstanceAndNotChildren(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        AdocFile adocFile = new AdocFile(filePath);
        adocFile.parsingChildrenFile = false;
        return adocFile.init();
    }

    /**
     * 解析文件
     */
    private @Nullable AdocFile init() {
        AdocFile adocFile = newFile().each(new MatchLine(this));
        if (adocFile != null) {
            if (parsingChildrenFile) {
                this.scanChildrenFile();
            }
            this.titleBuildRelationships();
        }
        return adocFile;
    }

    /**
     * 扫描子文件
     */
    private void scanChildrenFile() {
        if (CollectionUtils.isEmpty(includeLines)) {
            return;
        }
        includeLines.forEach(includeLine -> {
            String targetFilePath = includeLine.getIncludeSyntax().getTargetFilePath();
            AdocFile adocFile = AdocFile.getInstance(targetFilePath);
            includeLine.setTargetFile(adocFile);
        });
    }

    /**
     * 标题建立关系
     */
    private void titleBuildRelationships() {
        List<BaseLine> grammars = Stream.of(includeLines, titleLines).flatMap(List::stream).collect(Collectors.toList());
        // 处理titleList的关系
        grammars.stream()
                .filter(grammar -> !(grammar instanceof IncludeLine && ((IncludeLine) grammar).getTargetTitle() == null))
                .sorted(Comparator.comparing(BaseLine::getLineNumber))
                .reduce((a, b) -> {
                    //标题 标题
                    if (a instanceof TitleLine && b instanceof TitleLine) {
                        TitleLine parent = ((TitleLine) a).findParentOf((TitleLine) b);
                        if (parent == null) {
                            return a;
                        }
                        parent.addChildrenTitle((TitleLine) b);
                        return b;
                    }
                    //标题 include
                    if (a instanceof TitleLine && b instanceof IncludeLine) {
                        TitleLine targetTitle = ((IncludeLine) b).getTargetTitle();
                        TitleLine parent = ((TitleLine) a).findParentOf(targetTitle);
                        if (parent == null) {
                            return a;
                        }
                        parent.addChildrenTitle(targetTitle);
                        return a;
                    }
                    //include include
                    if (a instanceof IncludeLine && b instanceof IncludeLine) {
                        ((IncludeLine) a).getTargetTitle().findParentOf(((IncludeLine) b).getTargetTitle());
                        return ((IncludeLine) a).getTargetTitle().getParentTitle();
                    }
                    //include 标题
                    if (a instanceof IncludeLine && b instanceof TitleLine) {
                        ((IncludeLine) a).getTargetTitle().findParentOf((TitleLine) b).addChildrenTitle((TitleLine) b);
                        return b;
                    }
                    throw new RequestParamsValidException();
                });
    }

    /**
     * 遍历文件内容
     */
    private @NotNull IForEach newFile() {
        String absolutePath = this.getAbsolutePath();
        if (StringUtils.isBlank(absolutePath)) {
            return IForEach.empty_each;
        }
        File file = new File(absolutePath);
        if (!file.exists() || !file.isFile()) {
            return IForEach.empty_each;
        }
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            return matchLine -> {
                if (matchLine == null) {
                    return null;
                }
                MutableInt lineNumber = new MutableInt(0);
                br.lines().forEach(lineContent -> {
                    lineNumber.increment();
                    matchLine.match(new LineContent()
                            .setNumber(lineNumber.getValue())
                            .setText(lineContent));
                });
                try {
                    fr.close();
                    br.close();
                } catch (Exception e) {
                    log.error("[文件流关闭失败], filePath={}", filePath, e);
                }
                return this;
            };
        } catch (Exception e) {
            log.error("[文件读取失败], filePath={}", filePath, e);
            return IForEach.empty_each;
        }
    }

    /**
     * 获得绝对路径
     */
    private @Nullable String getAbsolutePath() {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        String projectPath = new ProBeanPath().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return null;
        }
        return projectPath + filePath;
    }

    /**
     * 获得根标题
     */
    public @Nullable TitleLine getRootTitle() {
        if (CollectionUtils.isEmpty(titleLines)) {
            return null;
        }
        return titleLines.get(0);
    }

    /**
     * 获得当前标题或所属标题, 通过行号
     */
    public @Nullable TitleLine getTitleByLineNumber(int lineNumber) {
        if (lineNumber < 0 || CollectionUtils.isEmpty(titleLines)) {
            return null;
        }
        TitleLine titleLine = titleLines.get(0);
        if (lineNumber <= titleLine.getLineNumber()) {
            return titleLine;
        }
        for (int i = 1; i < titleLines.size(); i++) {
            TitleLine first = titleLines.get(i);
            TitleLine second = Optional.of(i + 1)
                    .filter(next -> next < titleLines.size())
                    .map(titleLines::get)
                    .orElse(null);
            if (first.getLineNumber() <= lineNumber && (second == null || second.getLineNumber() > lineNumber)) {
                return first;
            }
        }
        return null;
    }

    interface IForEach {
        IForEach empty_each = forEach -> null;

        @Nullable AdocFile each(IMatchLine forEach);
    }

    interface IMatchLine {
        void match(LineContent lineContent);
    }

    @RequiredArgsConstructor
    class MatchLine implements IMatchLine {

        @NonNull
        private AdocFile adocFile;

        @Override
        public void match(LineContent lineContent) {
            if (matchTitle(lineContent) != null) {
                return;
            }
            if (matchInclude(lineContent) != null) {
                return;
            }
        }

        public @Nullable TitleLine matchTitle(LineContent lineContent) {
            if (lineContent == null) {
                return null;
            }
            TitleSyntax syntax = new TitleSyntax().init(lineContent.text);
            if (syntax == null) {
                return null;
            }
            TitleLine titleLine = new TitleLine();
            titleLine.setTitleSyntax(syntax)
                    .setAdocFile(adocFile)
                    .setLineNumber(lineContent.number);
            titleLines.add(titleLine);
            return titleLine;
        }

        public @Nullable IncludeLine matchInclude(LineContent lineContent) {
            if (lineContent == null) {
                return null;
            }
            IncludeSyntax syntax = new IncludeSyntax().init(lineContent.text);
            if (syntax == null) {
                return null;
            }
            IncludeLine includeLine = new IncludeLine();
            includeLine.setIncludeSyntax(syntax)
                    .setAdocFile(adocFile)
                    .setLineNumber(lineContent.number);
            includeLines.add(includeLine);
            return includeLine;
        }
    }

    @Data
    class LineContent {
        private int number;
        private String text;
    }

}
