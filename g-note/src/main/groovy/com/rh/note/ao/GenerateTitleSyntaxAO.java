package com.rh.note.ao;

import com.rh.note.component.AdocTextPane;
import org.jetbrains.annotations.NotNull;

/**
 * 生成title块, 通过被选择的文本
 */
public class GenerateTitleSyntaxAO {

    /**
     * 文件路径
     */
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public @NotNull GenerateTitleSyntaxAO setTextPane(@NotNull AdocTextPane textPane) {
        filePath = textPane.getBeanPath().getBeanPath();
        return this;
    }
}
