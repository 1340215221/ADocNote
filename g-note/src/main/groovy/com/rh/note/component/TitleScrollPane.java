package com.rh.note.component;

import com.rh.note.bean.IAdocFile;

import javax.swing.*;

/**
 * 编辑区滚动面板
 */
public class TitleScrollPane extends JScrollPane {

    private IAdocFile adocFile;

    public IAdocFile getAdocFile() {
        return adocFile;
    }

    public void setAdocFile(IAdocFile adocFile) {
        this.adocFile = adocFile;
    }
}
