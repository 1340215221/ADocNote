package com.rh.note.grammar.custom;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表格快捷语法
 * 格式: tab[数字:列数] ([表名])
 * 例子: tab3 基本类型大小
 */
@Data
public class TableCustomGrammar {

    /**
     * 匹配正则语法
     */
    private static String regex = "^tab([0-9]+)(?:\\s+([0-9a-zA-Z\\u4e00-\\u9fa5_,\\\\.\\-\\s]+?))??\\s*$";
    /**
     * 列数
     */
    private Integer cols;
    /**
     * 表名
     */
    private String tableName;

    public TableCustomGrammar init(String lineContent) {
        if (StringUtils.isBlank(lineContent)) {
            return null;
        }
        Matcher matcher = Pattern.compile(regex).matcher(lineContent);
        if (!matcher.find()) {
            return null;
        }
        cols = Integer.valueOf(matcher.group(1));
        tableName = matcher.group(2);
        return this;
    }

    /**
     * 生成语法块内容
     */
    public String generateGrammar() {
        StringBuilder grammarContext = new StringBuilder();
        if (StringUtils.isNotBlank(tableName)) {
            grammarContext.append(".").append(tableName);
        }
        grammarContext.append("[cols=").append(cols).append("]").append("\n")
                .append("|===").append("\n")
                .append("\n");
        for (int i = 0; i < cols; i++) {
            grammarContext.append("|").append("\n");
        }
        grammarContext.append("\n")
                .append("|===").append("\n");
        return grammarContext.toString();
    }
}
