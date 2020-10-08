package com.rh.note.line;

import com.rh.note.base.BaseLine;
import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.exception.RequestParamsValidException;
import com.rh.note.path.TitleBeanPath;
import com.rh.note.syntax.TitleSyntax;
import com.rh.note.util.TreeNodeIconUtil;
import com.rh.note.vo.ITitleLineVO;
import lombok.Data;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JTextPane;
import javax.swing.text.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 标题行
 */
@Data
public class TitleLine extends BaseLine implements ITitleLineVO {
    /**
     * 子标题
     */
    private final List<TitleLine> childrenTitles = new ArrayList<>();
    /**
     * 语法对象
     */
    private TitleSyntax titleSyntax;

    /**
     * 指定解析第几行的内容
     */
    public static @Nullable TitleLine parseByLineNumber(JTextPane textPane, Integer lineNumber) {
        if (textPane == null || lineNumber == null) {
            return null;
        }
        Element rootElement = textPane.getDocument().getDefaultRootElement();
        Element element = rootElement.getElement(lineNumber - 1);
        if (element == null) {
            return null;
        }
        try {
            String lineContent = textPane.getText(element.getStartOffset(), element.getEndOffset() - element.getStartOffset());
            TitleSyntax titleSyntax = new TitleSyntax().init(lineContent);
            if (titleSyntax == null) {
                return null;
            }
            TitleLine titleLine = new TitleLine();
            titleLine.titleSyntax = titleSyntax;
            return titleLine;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 添加子标题
     */
    public void addChildrenTitle(TitleLine titleLine) {
        if (titleLine == null) {
            return;
        }
        childrenTitles.add(titleLine);
        titleLine.setParentTitle(this);
    }

    /**
     * 查找目标的父标题
     */
    public TitleLine findParentOf(TitleLine titleLine) {
        if (titleLine == null) {
            return null;
        }
        if (titleSyntax.getLevel() < titleLine.getTitleSyntax().getLevel()) {
            return this;
        }
        if (parentTitle == null) {
            return null;
        }
        return parentTitle.findParentOf(titleLine);
    }

    @Override
    public String getTitleName() {
        return titleSyntax.getTitleName();
    }

    public @NotNull TitleBeanPath getBeanPath() {
        String filePath = getAdocFile().getFilePath();
        String titlePath = getTitlePath();
        if (StringUtils.isBlank(titlePath)) {
            throw new RequestParamsValidException();
        }
        return TitleBeanPath.getInstance(filePath, titlePath);
    }

    @Override
    public String getBeanPathStr() {
        String filePath = getAdocFile().getFilePath();
        String titlePath = getTitlePath();
        if (StringUtils.isBlank(titlePath)) {
            throw new RequestParamsValidException();
        }
        return filePath + "#" + titlePath;
    }

    @Override
    public Icon getTreeNodeIcon() {
        if (StringUtils.isBlank(getFilePath())) {
            return TreeNodeIconUtil.unknown;
        }
        // 判断是否为根标题
        boolean isRoot = parentTitle == null || !getFilePath().equals(parentTitle.getFilePath());
        if (AdocFileTypeEnum.readme.matchByFPath(getFilePath()) && isRoot) {
            return TreeNodeIconUtil.readmeRoot;
        }
        if (AdocFileTypeEnum.readme.matchByFPath(getFilePath())) {
            return TreeNodeIconUtil.readmeChildren;
        }
        if (AdocFileTypeEnum.towLevel.matchByFPath(getFilePath()) && isRoot) {
            return TreeNodeIconUtil.twoLevelRoot;
        }
        if (AdocFileTypeEnum.towLevel.matchByFPath(getFilePath())) {
            return TreeNodeIconUtil.twoLevelChildren;
        }
        if (AdocFileTypeEnum.content.matchByFPath(getFilePath())) {
            return TreeNodeIconUtil.content;
        }
        return TreeNodeIconUtil.unknown;
    }

    public String getFilePath() {
        return adocFile.getFilePath();
    }

    /**
     * 获得标题路径
     */
    public @Nullable String getTitlePath() {
        List<TitleLine> parentTitles = getParentTitles();
        if (CollectionUtils.isEmpty(parentTitles)) {
            return null;
        }
        return parentTitles.stream()
                .map(TitleLine::getTitleName)
                .collect(Collectors.joining("."));
    }

    /**
     * 获得所有父标题
     */
    public List<TitleLine> getParentTitles() {
        List<TitleLine> list = new ArrayList<>();
        addParentTitles(list);
        return list;
    }

    /**
     * 获得所有父标题--递归
     */
    private void addParentTitles(List<TitleLine> resultList) {
        TitleLine parentTitle = getParentTitle();
        if (parentTitle != null) {
            parentTitle.addParentTitles(resultList);
        }
        resultList.add(this);
    }
}
