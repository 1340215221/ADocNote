package com.rh.note.line;

import com.rh.note.base.BaseLine;
import lombok.Data;

/**
 * 普通文本行
 */
@Data
public class TextLine extends BaseLine {
    /**
     * 内容
     */
    private String text;
}
