package com.rh.note.line;

import com.rh.note.base.BaseLine;
import com.rh.note.bean.ITitleLine;
import com.rh.note.syntax.TitleSyntax;
import lombok.Getter;

import javax.swing.Icon;
import java.awt.Color;
import java.util.List;

/**
 * 标题行
 */
@Getter
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
}
