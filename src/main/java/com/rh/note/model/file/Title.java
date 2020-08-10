package com.rh.note.model.file;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题
 */
@Getter
@Setter
public class Title {

    /**
     * 父标签
     */
    private Title parentTitle;
    /**
     * 子标签
     */
    @Getter
    private List<Title> childrenTitle = new ArrayList<>();
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
    public void addChildrenTitle(Title title) {
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
