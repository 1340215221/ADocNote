package com.rh.note.line;

import com.rh.note.bean.ITitleGrammar;
import com.rh.note.file.AdocFile;
import lombok.Getter;

import java.util.List;

/**
 * 标题行
 */
@Getter
public class TitleLine implements ITitleGrammar {

    /**
     * 所属文件
     */
    private AdocFile adocFile;
    /**
     * 行号
     */
    private Integer lineNumber;
    /**
     * 父标题
     */
    private TitleLine parentTitle;
    /**
     * 子标题
     */
    private List<TitleLine> childrenTitles;

}
