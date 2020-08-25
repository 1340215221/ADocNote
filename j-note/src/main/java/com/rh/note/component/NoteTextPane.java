package com.rh.note.component;

import com.rh.note.file.AdocFile;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.swing.*;

/**
 * 自定义编辑区
 */
@RequiredArgsConstructor
public class NoteTextPane extends JTextPane {

    /**
     * adoc对象
     */
    @NonNull
    private final AdocFile adocFile;

    /**
     * 获得编辑文件路径
     */
    public String getFilePath() {
        return adocFile.getFilePath();
    }

}
