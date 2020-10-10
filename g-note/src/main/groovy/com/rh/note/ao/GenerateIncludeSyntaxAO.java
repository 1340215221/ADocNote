package com.rh.note.ao;

import com.rh.note.component.AdocTextPane;
import org.jetbrains.annotations.NotNull;

/**
 * 生成include块, 通过被选择文本--参数
 */
public class GenerateIncludeSyntaxAO {

    /**
     * 文件路径
     */
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public GenerateIncludeSyntaxAO setFilePath(@NotNull AdocTextPane textPane) {
        filePath = textPane.getBeanPath().getBeanPath();
        return this;
    }
}
