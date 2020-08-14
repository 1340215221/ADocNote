package com.rh.note.file;

import com.rh.note.common.IAdocFile;
import com.rh.note.grammar.IncludeGrammar;
import com.rh.note.grammar.TitleGrammar;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * adoc语法文件
 */
@Data
public class AdocFile implements IAdocFile {
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 标题
     */
    private List<TitleGrammar> titleGrammars;
    /**
     * include
     */
    private List<IncludeGrammar> includeGrammars;

    public void init(IAdocFile adocFile) {
        if (adocFile == null || StringUtils.isBlank(adocFile.getFilePath())) {
            return;
        }
        filePath = adocFile.getFilePath();
        File file = new File(adocFile.getFilePath());
        this.read(file).forEach((lineNumber, lineContent) -> {
            this.matchTitle(lineNumber, lineContent);
            this.matchInclude(lineNumber, lineContent);
        });
        this.scanChildrenFile();
        this.titleBuildRelationships();
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
        // todo
        // 处理titleList的关系
        // 处理include中的title的关系
    }

    /**
     * 匹配处理include
     */
    private void matchInclude(Integer lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent) || CollectionUtils.isEmpty(includeGrammars)) {
            return;
        }
        Matcher matcher = Pattern.compile("^\\s*include::([^\\.]+)\\.(\\S+)\\[\\S*\\]$").matcher(lineContent);
        if (!matcher.find()) {
            return;
        }
        IncludeGrammar includeGrammar = new IncludeGrammar().setFilePath(filePath).setLineNumber(lineNumber);
        includeGrammars.add(includeGrammar);
    }

    /**
     * 匹配处理标题
     */
    private void matchTitle(Integer lineNumber, String lineContent) {
        if (StringUtils.isBlank(lineContent) || CollectionUtils.isEmpty(titleGrammars)) {
            return;
        }
        Matcher matcher = Pattern.compile("^(=+)\\s(\\S+)\\s*$").matcher(lineContent);
        if (!matcher.find()) {
            return;
        }
        String titleLevel = matcher.group(1);
        String titleName = matcher.group(2);
        TitleGrammar titleGrammar = new TitleGrammar().setLevel(titleLevel.length())
                .setName(titleName)
                .setFilePath(filePath)
                .setLineNumber(lineNumber);
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
     * 遍历文件每一行
     */
    public IForEach read(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return empty_foreach_impl;
        }

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)
        ) {
            LineNumber lineNumber = new LineNumber();
            return handleLine -> br.lines().forEach(lineContent -> handleLine.handle(lineNumber.next(), lineContent));
        } catch (Exception e) {
            return empty_foreach_impl;
        }
    }

    private static final IForEach empty_foreach_impl = forEach -> {};

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
