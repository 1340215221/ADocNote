package com.rh.note.ao;

import com.rh.note.common.BaseAO;
import lombok.Data;

import javax.swing.text.Element;

/**
 * 待完成语法 参数
 */
@Data
public class SelectAndReplaceAO implements BaseAO {
    /**
     * 选择范围
     */
    private Integer startOffset;
    private Integer endOffset;
    /**
     * 替换内容
     */
    private String replaceContent;
    /**
     * 是光标行偏移量
     */
    private boolean isCaretLineOffset = true;

    @Override
    public boolean checkMissRequiredParams() {
        return startOffset == null || endOffset == null || replaceContent == null;
    }

    /**
     * 完善偏移量
     */
    public void completeOffset(Element element) {
        if (element == null) {
            return;
        }
        startOffset += element.getStartOffset();
        endOffset += element.getStartOffset();
    }
}
