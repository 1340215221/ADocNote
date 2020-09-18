package com.rh.note.file;

import com.rh.note.base.BaseLine;
import com.rh.note.bean.IAdocFile;
import com.rh.note.exception.ApplicationException;
import com.rh.note.exception.ErrorCode;
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        this.parsingFileContent();
        return this;
    }

    /**
     * 得到根标题
     */
    @Override
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
    private void parsingFileContent() {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        File file = new File(this.getAbsolutePath());
        this.read(file).forEach((lineNumber, lineContent) -> {
            boolean isMatch = this.matchTitle(lineNumber, lineContent)
                    || this.matchInclude(lineNumber, lineContent)
                    || this.matchBlank(lineNumber, lineContent);
            if (!isMatch) {
                this.matchText(lineNumber, lineContent);
            }
        });
        this.scanChildrenFile();
        this.titleBuildRelationships();
    }

    /**
     * 匹配普通文本
     */
    private void matchText(Integer lineNumber, String lineContent) {
        TextLine textLine = new TextLine();
        textLine.init(lineContent)
                .setLineNumber(lineNumber)
                .setAdocFile(this);
        textLines.add(textLine);
    }

    /**
     * 匹配空白行
     */
    private boolean matchBlank(Integer lineNumber, String lineContent) {
        BlankLine blankLine = new BlankLine().init(lineContent);
        if (blankLine == null) {
            return false;
        }
        blankLine.setLineNumber(lineNumber)
                .setAdocFile(this);
        blankLines.add(blankLine);
        return true;
    }

    /**
     * 标题建立关系
     */
    private void titleBuildRelationships() {
        List<BaseLine> grammars;
        // 处理titleList的关系
        grammars = new ArrayList<>(titleLines.size() + includeLines.size() + blankLines.size() + textLines.size());
        grammars.addAll(titleLines);
        grammars.addAll(includeLines);
        grammars.addAll(blankLines);
        grammars.addAll(textLines);
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
                    // 标题 非标题
                    if (a instanceof TitleLine) {
                        b.setParentTitle((TitleLine) a);
                        return a;
                    }
                    // 非标题 标题
                    if (b instanceof TitleLine) {
                        TitleLine tempParentTitle = a.getParentTitle();
                        if (tempParentTitle == null) {
                            return b;
                        }
                        TitleLine parent = tempParentTitle.findParentOf((TitleLine) b);
                        if (parent == null) {
                            return tempParentTitle;
                        }
                        parent.addChildrenTitle((TitleLine) b);
                        return b;
                    }
                    // 非标题 非标题
                    TitleLine parentTitle = a.getParentTitle();
                    if (parentTitle == null) {
                        return b;
                    }
                    b.setParentTitle(parentTitle);
                    return parentTitle;
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
            adocFile.init(includeLine.getIncludeSyntax().getTargetFilePath());
            includeLine.setTargetFile(adocFile);
        });
    }

    /**
     * 匹配处理include
     */
    private boolean matchInclude(Integer lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return false;
        }
        IncludeSyntax includeSyntax = new IncludeSyntax().init(lineContent);
        if (includeSyntax == null) {
            return false;
        }
        IncludeLine includeLine = (IncludeLine) new IncludeLine()
                .setIncludeSyntax(includeSyntax)
                .setAdocFile(this)
                .setLineNumber(lineNumber);
        includeLines.add(includeLine);
        return true;
    }

    /**
     * 匹配处理标题
     */
    private boolean matchTitle(Integer lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return false;
        }
        TitleSyntax titleSyntax = new TitleSyntax().init(lineContent);
        if (titleSyntax == null) {
            return false;
        }
        TitleLine titleLine = (TitleLine) new TitleLine()
                .setTitleSyntax(titleSyntax)
                .setLineNumber(lineNumber)
                .setAdocFile(this);
        titleLines.add(titleLine);
        return true;
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
     * 获得文件名
     */
    public String getFileName() {
        return getRootTitle().getTitleName() + ".adoc";
    }

    /**
     * 获得行对象, 通过行号
     */
    public BaseLine getLineBeanByLineNumber(int lineNumber) {
        if (lineNumber < 1) {
            return null;
        }

        return Stream.of(titleLines, includeLines, blankLines, textLines)
                .flatMap(List::stream)
                .filter(line -> line.getLineNumber().equals(lineNumber))
                .findFirst()
                .orElse(null);
    }

    /**
     * 通过根标题初始化
     */
    public AdocFile init(TitleLine rootTitleLine) {
        if (rootTitleLine == null) {
            return null;
        }

        titleLines.add(rootTitleLine);
        BlankLine bl1 = (BlankLine) new BlankLine().setAdocFile(this).setLineNumber(1).setParentTitle(rootTitleLine.getParentTitle());
        BlankLine bl2 = (BlankLine) new BlankLine().setAdocFile(this).setLineNumber(2).setParentTitle(rootTitleLine.getParentTitle());
        BlankLine bl3 = (BlankLine) new BlankLine().setAdocFile(this).setLineNumber(4).setParentTitle(rootTitleLine);
        BlankLine bl4 = (BlankLine) new BlankLine().setAdocFile(this).setLineNumber(5).setParentTitle(rootTitleLine);
        blankLines.add(bl1);
        blankLines.add(bl2);
        blankLines.add(bl3);
        blankLines.add(bl4);

        String content = this.toContent();
        this.writeToFile(content);
        return this;
    }

    /**
     * 将当前adoc_file对象内容写入到文件
     */
    private void writeToFile(String content) {
        String absolutePath = getAbsolutePath();
        if (StringUtils.isBlank(absolutePath)) {
            return;
        }
        File file = new File(absolutePath);
        if (file.exists()) {
            return;
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new ApplicationException(ErrorCode.file_create_fail);
        }
        try (FileWriter fw = new FileWriter(file); BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(content);
        } catch (Exception e) {
            throw new ApplicationException(ErrorCode.file_write_fail);
        }
    }

    /**
     * 生成内容
     */
    private String toContent() {
        return Stream.of(titleLines, includeLines, textLines, blankLines)
                .flatMap(List::stream)
                .sorted(Comparator.comparing(BaseLine::getLineNumber))
                .map(BaseLine::toContent)
                .collect(Collectors.joining("\n"));
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
