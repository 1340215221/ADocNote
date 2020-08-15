package com.rh.note.file;

import com.rh.note.common.IAdocFile;
import com.rh.note.common.IGrammar;
import com.rh.note.constant.ErrorMessage;
import com.rh.note.exception.AdocException;
import com.rh.note.grammar.IncludeGrammar;
import com.rh.note.grammar.TitleGrammar;
import com.rh.note.grammar.UnknownGrammar;
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
import java.util.stream.Collectors;

/**
 * adoc语法文件
 */
@Getter
public class AdocFile implements IAdocFile {
    /**
     * 文件路径
     */
    @Setter
    @Getter
    private String filePath;
    /**
     * 标题
     */
    private List<TitleGrammar> titleGrammars;
    /**
     * include
     */
    private List<IncludeGrammar> includeGrammars;
    /**
     * 普通文本
     */
    private List<UnknownGrammar> unknownGrammars;

    public void init(IAdocFile adocFile) {
        if (adocFile == null || StringUtils.isBlank(adocFile.getFilePath())) {
            return;
        }
        filePath = adocFile.getFilePath();
        File file = new File(adocFile.getAbsolutePath());
        this.read(file).forEach((lineNumber, lineContent) -> {
            this.matchTitle(lineNumber, lineContent);
            this.matchInclude(lineNumber, lineContent);
        });
        this.scanChildrenFile();
        this.titleBuildRelationships();
        adocFile.copy(this);
    }

    /**
     * 扫描子文件
     */
    private void scanChildrenFile() {
        if (CollectionUtils.isEmpty(includeGrammars)) {
            return;
        }
        includeGrammars.forEach(includeGrammar -> {
            AdocFile adocFile = new AdocFile();
            if (titleGrammars != null) {
                adocFile.title();
            }
            if (includeGrammars != null) {
                adocFile.include();
            }
            adocFile.init(includeGrammar.buildSimpleTargetAdocFile());
            includeGrammar.setTargetTitle(adocFile.getRootTitle());
        });
    }

    /**
     * 得到根标题
     */
    private TitleGrammar getRootTitle() {
        if (CollectionUtils.isEmpty(titleGrammars)) {
            return null;
        }
        return titleGrammars.stream().min(Comparator.comparing(TitleGrammar::getLevel)).orElse(null);
    }

    /**
     * 标题建立关系
     */
    private void titleBuildRelationships() {
        if (CollectionUtils.isEmpty(titleGrammars)) {
            return;
        }
        List<IGrammar> grammars;
        // 处理titleList的关系
        if (CollectionUtils.isEmpty(includeGrammars)) {
            grammars = (List) titleGrammars;
        } else {
            // 处理include中的title的关系
            grammars = new ArrayList<>(titleGrammars.size() + includeGrammars.size());
            grammars.addAll(titleGrammars);
            grammars.addAll(includeGrammars);
        }
        grammars.stream()
                .sorted(Comparator.comparing(IGrammar::getLineNumber))
                .reduce((a, b) -> {
                    //标题 标题
                    if (a instanceof TitleGrammar && b instanceof TitleGrammar) {
                        ((TitleGrammar) a).findParentOf((TitleGrammar) b).addChildrenTitle((TitleGrammar) b);
                        return b;
                    }
                    //标题 include
                    if (a instanceof TitleGrammar && b instanceof IncludeGrammar) {
                        TitleGrammar targetTitle = ((IncludeGrammar) b).getTargetTitle();
                        ((TitleGrammar) a).findParentOf(targetTitle).addChildrenTitle(targetTitle);
                        return a;
                    }
                    //include include
                    if (a instanceof IncludeGrammar && b instanceof IncludeGrammar) {
                        ((IncludeGrammar) a).getTargetTitle().findParentOf(((IncludeGrammar) b).getTargetTitle());
                        return ((IncludeGrammar) a).getTargetTitle().getParentTitle();
                    }
                    //include 标题
                    if (a instanceof IncludeGrammar && b instanceof TitleGrammar) {
                        ((IncludeGrammar) a).getTargetTitle().findParentOf((TitleGrammar) b).addChildrenTitle((TitleGrammar) b);
                        return b;
                    }
                    throw new AdocException(ErrorMessage.PARAMETER_ERROR);
                });
    }

    /**
     * 匹配处理include
     */
    private void matchInclude(Integer lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent) || includeGrammars == null) {
            return;
        }
        IncludeGrammar includeGrammar = new IncludeGrammar().init(lineContent);
        if (includeGrammar == null) {
            return;
        }
        includeGrammar.setFilePath(filePath);
        includeGrammar.setLineNumber(lineNumber);
        includeGrammars.add(includeGrammar);
    }

    /**
     * 匹配处理标题
     */
    private void matchTitle(Integer lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent) || titleGrammars == null) {
            return;
        }
        TitleGrammar titleGrammar = new TitleGrammar().init(lineContent);
        if (titleGrammar == null) {
            return;
        }
        titleGrammar.setFilePath(filePath);
        titleGrammar.setLineNumber(lineNumber);
        titleGrammars.add(titleGrammar);
    }

    /**
     * 设置解析 标题
     */
    public AdocFile title() {
        titleGrammars = new ArrayList<>();
        return this;
    }

    /**
     * 设置解析 include
     */
    public AdocFile include() {
        includeGrammars = new ArrayList<>();
        return this;
    }

    /**
     * 设置保存普通文本
     */
    public AdocFile unknown() {
        unknownGrammars = new ArrayList<>();
        return this;
    }

    /**
     * 遍历文件每一行
     */
    public IForEach read(File file) {
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
                throw new AdocException(ErrorMessage.file_read_failed, e);
            }
        };
    }

    private static final IForEach empty_foreach_impl = forEach -> {};

    /**
     * 通过include生成adoc文件
     */
    public AdocFile init(IncludeGrammar includeGrammar) {
        if (includeGrammar == null) {
            return null;
        }
        title().unknown();
        LineNumber lineNumber = new LineNumber();
        unknownGrammars.add(UnknownGrammar.emptyLine().setLineNumber(lineNumber.next()));
        titleGrammars.add(includeGrammar.copyTo().setLineNumber(lineNumber.next()));
        unknownGrammars.add(UnknownGrammar.emptyLine().setLineNumber(lineNumber.next()));
        filePath = includeGrammar.getTargetFilePath();
        return this;
    }

    @Override
    public String toString() {
        Integer countLineNumber = 0;
        if (CollectionUtils.isNotEmpty(includeGrammars)) {
            countLineNumber += includeGrammars.size();
        }
        if (CollectionUtils.isNotEmpty(titleGrammars)) {
            countLineNumber += titleGrammars.size();
        }
        if (CollectionUtils.isNotEmpty(unknownGrammars)) {
            countLineNumber += unknownGrammars.size();
        }
        List<IGrammar> grammars = new ArrayList<>(countLineNumber);
        if (CollectionUtils.isNotEmpty(includeGrammars)) {
            grammars.addAll(includeGrammars);
        }
        if (CollectionUtils.isNotEmpty(titleGrammars)) {
            grammars.addAll(titleGrammars);
        }
        if (CollectionUtils.isNotEmpty(unknownGrammars)) {
            grammars.addAll(unknownGrammars);
        }
        return grammars.stream()
                .sorted(Comparator.comparing(IGrammar::getLineNumber))
                .map(IGrammar::toLineContent)
                .collect(Collectors.joining());
    }

    /**
     * 每一行内容查看接口
     */
    private interface IHandleLine {
        void handle(Integer lineNumber, String lineContent);
    }

    /**
     * 编辑文件操作
     */
    private interface IForEach {
        void forEach(IHandleLine forEach);
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
}
