package com.rh.note.line;

import com.rh.note.base.BaseLine;
import com.rh.note.bean.ITitleLine;
import com.rh.note.constants.AdocFileTypeEnum;
import com.rh.note.syntax.TitleSyntax;
import com.rh.note.util.TreeNodeIconUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.swing.Icon;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * 标题行
 */
@Data
public class TitleLine extends BaseLine implements ITitleLine {
    /**
     * 子标题
     */
    private final List<TitleLine> childrenTitles = new ArrayList<>();
    /**
     * 语法对象
     */
    private TitleSyntax titleSyntax;

    @Override
    public String getTitleName() {
        return titleSyntax.getTitleName();
    }

    @Override
    public String getFilePath() {
        return adocFile.getFilePath();
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

    @Override
    public Color getColor() {
        return null;
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
}
