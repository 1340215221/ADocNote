package com.rh.note.component;

import com.rh.note.bean.IAdocFile;

import javax.swing.JTextPane;

/**
 * 编辑区
 */
public class AdocTextPane extends JTextPane {

    /**
     * adoc对象
     */
    private IAdocFile adocFile;

    public IAdocFile getAdocFile() {
        return adocFile;
    }

    public void setAdocFile(IAdocFile adocFile) {
        this.adocFile = adocFile;
    }
}
