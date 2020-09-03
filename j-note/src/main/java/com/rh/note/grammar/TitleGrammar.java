package com.rh.note.grammar;

import com.rh.note.common.IGrammar;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * 标题
 */
@Getter
@Setter
public class TitleGrammar implements IGrammar,ITitleGrammar {

    /**
     * 父标题
     */
    private TitleGrammar parentTitle;
    /**
     * 子标题
     */
    @Setter(AccessLevel.NONE)
    private List<TitleGrammar> childrenTitle = new ArrayList<>();
    /**
     * 标题名
     */
    private String name;
    /**
     * 所在文件的行号
     */
    private Integer lineNumber;
    /**
     * 标签级别
     */
    private Integer level;
    /**
     * 地址
     */
    private String filePath;

    public TitleGrammar init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile("^(=+)\\s(\\S+)\\s*$").matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        String titleLevel = matcher.group(1);
        String titleName = matcher.group(2);
        level = titleLevel.length();
        name = titleName;
        return this;
    }

    /**
     * 添加子标题
     */
    public TitleGrammar addChildrenTitle(TitleGrammar titleGrammar) {
        if (titleGrammar != null) {
            childrenTitle.add(titleGrammar);
            titleGrammar.setParentTitle(this);
        }
        return this;
    }

    /**
     * 获得 param 的父标题
     */
    public TitleGrammar findParentOf(@NonNull TitleGrammar titleGrammar) {
        if (level < titleGrammar.level) {
            return this;
        }
        if (parentTitle == null) {
            return null;
        }
        return parentTitle.findParentOf(titleGrammar);
    }

    /**
     * 获得导航栏按钮字体颜色, 通过文件所在位置
     * read 默认颜色
     * twoLevel 绿色
     * content 粉色
     */
    @Override
    public Color getColor() {
        if (filePath.contains("twoLevel")) {
            return Color.pink;
        }
        if (filePath.contains("content")) {
            return Color.green;
        }
        return Color.lightGray;
    }

    @Override
    public String toLineContent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("=");
        }
        return sb.append(" ").append(name).append(System.lineSeparator()).toString();
    }

    /**
     * 在标题列表显示的内容
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * 获得父标题
     */
    public List<TitleGrammar> getParentTitles() {
        List<TitleGrammar> resultList = new ArrayList<>();
        this.addParentTitle(this, resultList);
        return resultList;
    }

    private void addParentTitle(TitleGrammar titleGrammar, List<TitleGrammar> resultList) {
        TitleGrammar parentTitle = titleGrammar.getParentTitle();
        if (parentTitle != null) {
            this.addParentTitle(parentTitle, resultList);
        }
        resultList.add(titleGrammar);
    }

    /**
     * 查询子标题, 通过文件地址
     */
    public TitleGrammar getChildrenByFilePath(String childrenFilePath, String childrenTitleName) {
        if (StringUtils.isBlank(childrenFilePath) || StringUtils.isBlank(childrenTitleName)) {
            return null;
        }
        return childrenTitle.stream()
                .flatMap(this::getChildrenTitleStream)
                .filter(titleGrammar -> titleGrammar.getName().equals(childrenTitleName) && titleGrammar.getFilePath().equals(childrenFilePath))
                .findFirst()
                .orElse(null);
    }

    private Stream<TitleGrammar> getChildrenTitleStream(TitleGrammar titleGrammar) {
        if (titleGrammar == null) {
            return Stream.empty();
        }
        Stream<TitleGrammar> childrenTitleStream = titleGrammar.getChildrenTitle().stream()
                .flatMap(this::getChildrenTitleStream);
        return Stream.concat(Stream.of(titleGrammar), childrenTitleStream);
    }

}
