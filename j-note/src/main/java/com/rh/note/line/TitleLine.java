package com.rh.note.line;

import com.rh.note.base.BaseLine;
import com.rh.note.bean.ITitleLine;
import com.rh.note.syntax.TitleSyntax;
import lombok.Data;
import lombok.Getter;

import javax.swing.Icon;
import java.awt.Color;
import java.util.List;

/**
 * 标题行
 */
@Data
public class TitleLine extends BaseLine implements ITitleLine {
    /**
     * 子标题
     */
    private List<TitleLine> childrenTitles;
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
    public Icon getIcon() {
        return null;
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
