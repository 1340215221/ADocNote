package com.rh.note.grammar;

import com.rh.note.common.IGrammar;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 未知语法
 */
@Data
public class UnknownGrammar implements IGrammar {

    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 行号
     */
    private Integer lineNumber;
    /**
     * 行内容
     */
    private String lineContent;

    /**
     * 生成空白行
     */
    public static UnknownGrammar emptyLine() {
        return new UnknownGrammar().setLineContent(System.lineSeparator());
    }

    public UnknownGrammar init(String lineContent) {
        this.lineContent = StringUtils.isNotBlank(lineContent) ? lineContent : System.lineSeparator();
        return this;
    }

    @Override
    public String toLineContent() {
        return lineContent;
    }
}
