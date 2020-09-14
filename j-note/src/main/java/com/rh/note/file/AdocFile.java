package com.rh.note.file;

import com.rh.note.base.BaseLine;
import com.rh.note.bean.IAdocFile;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCode;
import com.rh.note.exception.IllegalArgumentException;
import com.rh.note.line.BlankLine;
import com.rh.note.line.IncludeLine;
import com.rh.note.line.TextLine;
import com.rh.note.line.TitleLine;
import com.rh.note.syntax.IncludeSyntax;
import com.rh.note.syntax.TitleSyntax;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * adoc文件对象
 */
public class AdocFile implements IAdocFile {

    /**
     * 文件项目路径
     */
    @Getter
    @Setter
    protected String filePath;
    /**
     * 标题内容
     */
    protected final List<TitleLine> titleLines = new ArrayList<>();
    /**
     * include内容
     */
    protected final List<IncludeLine> includeLines = new ArrayList<>();
    /**
     * 空白内容
     */
    protected final List<BlankLine> blankLines = new ArrayList<>();
    /**
     * 普通文本
     */
    protected final List<TextLine> textLines = new ArrayList<>();

    /**
     * 初始化,通过项目相对路径
     */
    public AdocFile init(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }

        this.filePath = filePath;
        this.parsingFileContent(filePath);
        return this;
    }

    /**
     * 得到根标题
     */
    public TitleLine getRootTitle() {
        if (CollectionUtils.isEmpty(titleLines)) {
            return null;
        }
        return titleLines.get(0);
    }

    /**
     * 获得项目绝对地址
     */
    public String getAbsolutePath() {
        String projectPath = new ProjectDirectory().getProjectPath();
        if (StringUtils.isBlank(projectPath)) {
            return null;
        }
        return projectPath + getFilePath();
    }

    /**
     * 解析文件内容
     */
    private void parsingFileContent(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        File file = new File(this.getAbsolutePath());
        this.read(file).forEach((lineNumber, lineContent) -> {
            this.matchTitle(lineNumber, lineContent);
            this.matchInclude(lineNumber, lineContent);
        });
        this.scanChildrenFile();
        this.titleBuildRelationships();
    }

    /**
     * 标题建立关系
     */
    private void titleBuildRelationships() {
        List<BaseLine> grammars;
        // 处理titleList的关系
        if (CollectionUtils.isEmpty(includeLines)) {
            grammars = (List) titleLines;
        } else {
            // 处理include中的title的关系
            grammars = new ArrayList<>(titleLines.size() + includeLines.size());
            grammars.addAll(titleLines);
            grammars.addAll(includeLines);
        }
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
                    throw new IllegalArgumentException();
                });
    }

    /**
     * 扫描子文件
     */
    private void scanChildrenFile() {
        if (CollectionUtils.isEmpty(includeLines)) {
            return;
        }
        includeLines.forEach(includeLine -> {
            AdocFile adocFile = new AdocFile();
            adocFile.init(includeLine.getTargetFile().getFilePath());
            includeLine.setTargetFile(adocFile);
        });
    }

    /**
     * 匹配处理include
     */
    private void matchInclude(Integer lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return;
        }
        IncludeSyntax includeSyntax = new IncludeSyntax().init(lineContent);
        if (includeSyntax == null) {
            return;
        }
        IncludeLine includeLine = new IncludeLine()
                .setIncludeSyntax(includeSyntax)
                .setTargetFile()
                .setAdocFile()
                .setLineNumber(lineNumber)
                .setParentTitle();
        includeLines.add(includeLine);
    }

    /**
     * 匹配处理标题
     */
    private void matchTitle(Integer lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return;
        }
        TitleSyntax titleSyntax = new TitleSyntax().init(lineContent);
        if (titleSyntax == null) {
            return;
        }
        TitleLine titleLine = new TitleLine()
                .setTitleSyntax()
                .setChildrenTitles()
                .setLineNumber(lineNumber)
                .setParentTitle()
                .setAdocFile();
        titleLines.add(titleLine);
    }

    /**
     * 遍历文件每一行
     */
    private IForEach read(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return empty_foreach_impl;
        }

        LineNumber lineNumber = new LineNumber();
        return handleLine -> {
            try (FileInputStream fis = new FileInputStream(file);
                 InputStreamReader isr = new InputStreamReader(fis);
                 BufferedReader br = new BufferedReader(isr)
            ) {
                br.lines().forEach(lineContent -> handleLine.handle(lineNumber.next(), lineContent));
            } catch (Exception e) {
                throw new ApplicationException(ErrorCode.file_read_failed, e);
            }
        };
    }

    /**
     * 行号记录
     */
    private class LineNumber {
        /**
         * 行号
         */
        private Integer lineNumber = 0;

        /**
         * 增加并获得行号
         */
        private Integer next() {
            lineNumber++;
            return lineNumber;
        }
    }

    private static final IForEach empty_foreach_impl = forEach -> {
    };

    /**
     * 编辑文件操作
     */
    private interface IForEach {
        void forEach(IHandleLine forEach);
    }

    /**
     * 每一行内容查看接口
     */
    private interface IHandleLine {
        void handle(Integer lineNumber, String lineContent);
    }

}
