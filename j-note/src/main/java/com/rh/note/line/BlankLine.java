package com.rh.note.line;

import com.rh.note.base.BaseLine;
import org.apache.commons.lang3.StringUtils;

/**
 * 空白行
 */
public class BlankLine extends BaseLine {
    public BlankLine init(String lineContent) {
        return StringUtils.isBlank(lineContent) ? this : null;
    }
}
