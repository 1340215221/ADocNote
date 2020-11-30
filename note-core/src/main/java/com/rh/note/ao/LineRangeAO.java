package com.rh.note.ao;

import com.rh.note.line.TitleLine;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * 行范围 参数
 */
@Getter
@Accessors(chain = true)
public class LineRangeAO {

    /**
     * 起始行索引
     */
    private int startLineIndex;
    /**
     * 结束行索引
     */
    private int endLineIndex;
    /**
     * 文件路径
     */
    @Setter
    @Getter
    private String filePath;

    /**
     * 设置下一个标题对象
     */
    public LineRangeAO setNextTitle(TitleLine titleLine) {
        endLineIndex =  titleLine != null ? titleLine.getLineNumber() - 1 - 1 : Integer.MAX_VALUE;
        return this;
    }

    /**
     * 设置开始行号
     */
    public LineRangeAO setStartLineNumber(int startLineNumber) {
        this.startLineIndex = startLineNumber - 1;
        return this;
    }

    /**
     * 校验数据合理性
     */
    public boolean isReasonable() {
        return StringUtils.isNotBlank(filePath) && startLineIndex > -1 && startLineIndex < endLineIndex;
    }
}
