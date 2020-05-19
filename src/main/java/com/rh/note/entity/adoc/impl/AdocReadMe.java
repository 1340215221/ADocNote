package com.rh.note.entity.adoc.impl;

import com.rh.note.constant.ErrorCodeEnum;
import com.rh.note.entity.adoc.AdocFile;
import com.rh.note.entity.adoc.AdocPart;
import com.rh.note.exception.AdocException;
import lombok.experimental.Delegate;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * adoc项目主文件
 */
public class AdocReadMe extends AdocFile {

    public static final String readMeFileName = "README.adoc";

    @Delegate
    private final List<AdocPart> parts = new ArrayList<>();

    @Override
    protected void initAdocCode(String name) {
        AdocDirectory adocDirectory = new AdocDirectory(name);
        adocDirectory.defaultConfig();
    }

}
