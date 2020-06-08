package com.rh.note.entity.adoc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 标题
 */
@Getter
@Setter
@NoArgsConstructor
public class AdocTitile {

    private String id;
    /**
     * 父标签
     */
    private AdocTitile parentTitle;
    /**
     * 子标签
     */
    @Getter
    private List<AdocTitile> childrenTitle = new ArrayList<>();
    private String displayName;
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
    private String path;

    /**
     * 添加
     */
    public void addChildrenTitle(AdocTitile adocTitile) {
        childrenTitle.add(adocTitile);
    }

    public String toString() {
        return this.displayName;
    }

}
