package com.rh.note.grammar;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题
 */
@Getter
@Setter
public class TitleGrammar {

    /**
     * 父标签
     */
    private TitleGrammar parentTitle;
    /**
     * 子标签
     */
    @Getter
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
    private String absolutePath;

    /**
     * 添加
     */
    public void addChildrenTitle(TitleGrammar title) {
        if (title == null) {
            return;
        }

        childrenTitle.add(title);
        title.setParentTitle(this);
    }

    @Override
    public String toString() {
        return name;
    }
}
