package com.rh.note.path;

import com.rh.note.base.ITitleBeanPath;
import org.apache.commons.lang3.StringUtils;

/**
 * 标题地址
 * todo
 */
public class TitleBeanPath implements ITitleBeanPath {

    /**
     * 文件地址
     */
    private String filePath;
    /**
     * 文件内标题路径
     */
    private String titlePath;

    private TitleBeanPath() {
    }

    @Override
    public void getColor() {
    }

    @Override
    public String getTitleName() {
        return null;
    }

    @Override
    public String getBeanPath() {
        return null;
    }
}
