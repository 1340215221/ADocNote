package com.rh.note.line;

import com.rh.note.base.BaseLine;
import com.rh.note.file.AdocFile;
import com.rh.note.syntax.IncludeSyntax;
import lombok.Data;

/**
 * 引用行
 */
@Data
public class IncludeLine extends BaseLine {
    /**
     * 指向文件
     */
    private AdocFile targetFile;
    /**
     * 语法对象
     */
    private IncludeSyntax includeSyntax;

    /**
     * 获得指向标题
     */
    public TitleLine getTargetTitle() {
        if (targetFile == null) {
            return null;
        }
        return targetFile.getRootTitle();
    }
}
