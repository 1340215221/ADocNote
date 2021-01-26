package com.rh.note.file;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * readMe标题文件
 */
public class ReadMeTitleFile extends AdocTitleFile {

    public ReadMeTitleFile() {
        super("README.adoc", new ArrayList<>());
    }

    @Override
    public @Nullable ReadMeTitleFile init() {
        return (ReadMeTitleFile) super.init();
    }
}
