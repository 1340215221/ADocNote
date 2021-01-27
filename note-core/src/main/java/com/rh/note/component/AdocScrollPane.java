package com.rh.note.component;

import com.rh.note.path.FileBeanPath;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

/**
 * 编辑区-滚动面板
 */
@Getter
@Setter
public class AdocScrollPane extends JScrollPane {
    /**
     * 对象地址
     */
    private FileBeanPath beanPath;
}
