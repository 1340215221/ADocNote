package com.rh.note.file;

import com.rh.note.collection.ReadTitleLineList;
import org.jetbrains.annotations.Nullable;

/**
 * readMe标题文件
 */
public class ReadMeTitleFile extends AdocTitleFile {

    public ReadMeTitleFile() {
        super("README.adoc", new ReadTitleLineList());
    }

    @Override
    public @Nullable ReadMeTitleFile init() {
        return (ReadMeTitleFile) super.init();
    }
}
