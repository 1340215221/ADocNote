package com.rh.note.entity.adoc.impl;

import com.rh.note.constant.BooleanEnum;
import com.rh.note.constant.DirectoryDoctypeEnum;
import com.rh.note.constant.DirectoryTopEnum;
import com.rh.note.entity.adoc.AdocBlock;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * adoc目录控件
 */
@Data
@RequiredArgsConstructor
public class AdocDirectory implements AdocBlock {

    private static final String firstVesion = "1.0";

    /**
     * 项目名
     */
    @NonNull
    private String name;

    /**
     * 作者
     */
    private String author;

    /**
     * 版本
     */
    private String version;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 文档格式
     * {@link com.rh.note.constant.DirectoryDoctypeEnum}
     */
    private String doctype;

    /**
     * 目录位置
     * {@link com.rh.note.constant.DirectoryTopEnum}
     */
    private String top;

    /**
     * 标题是否带编号
     * {@link com.rh.note.constant.BooleanEnum}
     */
    private Integer sectnums;

    /**
     * 默认配置
     */
    public void defaultConfig() {
        author = "阮航";
        version = firstVesion;
        createTime = System.currentTimeMillis();
        doctype = DirectoryDoctypeEnum.book.getValue();
        top = DirectoryTopEnum.left.getValue();
        sectnums = BooleanEnum.TRUE.getValue();
    }
}
