package com.rh.note.line;

import com.rh.note.common.IReadTitleLine;
import lombok.Data;

import java.util.List;

/**
 * 代理的空标题行对象
 */
@Data
public class ProxyTitleLine implements IReadTitleLine {
    /**
     * 子标题
     */
    private List<IReadTitleLine> childTitles;
}
