package com.rh.note.entity.adoc;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 标题
 */
@Data
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
    private List<AdocTitile> childrenTitle;
    private String displayName;
    /**
     * 在项目里的相对路径
     */
    private String filePath;
    /**
     * 所在文件的行号
     */
    private Integer lineNumber;

}
