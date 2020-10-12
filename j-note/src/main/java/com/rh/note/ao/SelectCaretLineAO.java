package com.rh.note.ao;

import lombok.Data;

/**
 * 选择光标所在行 参数
 */
@Data
public class SelectCaretLineAO {
    /**
     * 光标行起始偏移量
     */
    private int startOffset;
    /**
     * 长度
     */
    private int length;
}
