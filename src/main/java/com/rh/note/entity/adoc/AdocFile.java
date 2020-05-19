package com.rh.note.entity.adoc;

import java.io.File;
import java.util.List;

/**
 * adoc后缀的文件对象
 */
public abstract class AdocFile implements List<AdocPart> {

    protected String path;

    /**
     * 初始化adoc格式
     */
    public void init(String name, String path) {
        this.initFileInfo(path);
        this.initAdocCode(name);
    }

    /**
     * 初始化文件信息
     */
    protected void initFileInfo(String path){
        this.path = path;
    }

    /**
     * 初始化adoc代码
     */
    protected abstract void initAdocCode(String name);

}
