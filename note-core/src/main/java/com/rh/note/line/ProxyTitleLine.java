package com.rh.note.line;

import com.rh.note.collection.ReadTitleLineList;
import com.rh.note.common.IReadTitleLine;
import com.rh.note.common.ReadTitleLineImpl;
import lombok.Data;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Nullable;

/**
 * 代理的空标题行对象
 */
@Data
@Accessors(chain = true)
public class ProxyTitleLine implements IReadTitleLine {
    /**
     * 行号
     */
    private Integer lineNumber;
    /**
     * 子标题
     */
    private ReadTitleLineList childTitles;

    public @Nullable ReadTitleLineImpl getTargetTitle() {
        return childTitles != null ? childTitles.getRootTitleImpl() : null;
    }
}
