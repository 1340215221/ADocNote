package com.rh.note.component;

import cn.hutool.core.io.FileUtil;
import com.rh.note.path.FileBeanPath;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 编辑区-滚动面板
 */
@Getter
@Setter
public class TextScrollPane extends JScrollPane {
    /**
     * 对象地址
     */
    private FileBeanPath beanPath;

    /**
     * 文件名
     */
    public @NotNull String getFileName() {
        return FileUtil.getName(beanPath.getFilePath());
    }
}
