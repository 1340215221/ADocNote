package com.rh.note.line;

import com.rh.note.base.BaseLine;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 普通文本行
 */
@Data
@Accessors(chain = true)
public class TextLine extends BaseLine {
    /**
     * 内容
     */
    private String text;

    public TextLine init(String lineContent) {
        text = lineContent;
        return this;
    }
}
