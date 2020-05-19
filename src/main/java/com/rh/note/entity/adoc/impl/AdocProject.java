package com.rh.note.entity.adoc.impl;

import com.rh.note.entity.adoc.AdocFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * adoc项目
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class AdocProject {

    private Long id;
    /**
     * 项目绝对路径
     */
    @NonNull
    private String path;

    /**
     * 项目名
     */
    @NonNull
    private String name;

    /**
     * 项目主文件
     */
    private AdocFile mainAdocFile;

    /**
     * 初始化项目信息
     */
    public void initProjectInfo() {
        AdocReadMe readMe = new AdocReadMe();
        readMe.init(name, path);
        mainAdocFile = readMe;
    }

    /**
     * 初始化主文件
     */
    private void initMainAdocFile() {

    }

}
