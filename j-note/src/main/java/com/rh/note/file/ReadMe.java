package com.rh.note.file;

/**
 * readme文件
 */
public class ReadMe extends AdocFile {
    public ReadMe init() {
        return (ReadMe) super.init(getFilePath());
    }

    @Override
    public String getFilePath() {
        return "README.adoc";
    }
}
