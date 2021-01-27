package com.rh.note.component;

import com.rh.note.common.IFileBeanPath;
import com.rh.note.path.FileBeanPath;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;
import javax.swing.text.StyledDocument;

/**
 * 编辑面板
 */
@Getter
@Setter
@NoArgsConstructor
public class AdocTextPane extends JTextPane implements IFileBeanPath {
    /**
     * 对象地址
     */
    private FileBeanPath beanPath;

    public AdocTextPane(StyledDocument doc) {
        super(doc);
    }
}
